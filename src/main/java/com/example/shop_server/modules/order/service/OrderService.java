package com.example.shop_server.modules.order.service;

import com.example.shop_server.modules.enums.OrderEnum;
import com.example.shop_server.modules.order.OrderModel;
import com.example.shop_server.modules.order.dto.req.AddToCard;
import com.example.shop_server.modules.order.reqpository.OrderRepository;
import com.example.shop_server.modules.orderDetail.OrderDetailModel;
import com.example.shop_server.modules.orderDetail.OrderDetailRepository;
import com.example.shop_server.modules.orderDetail.service.OderDetailService;
import com.example.shop_server.modules.product.ProductModel;
import com.example.shop_server.modules.product.repository.ProductModelRepository;
import com.example.shop_server.modules.product.service.ProductService;
import com.example.shop_server.modules.user.UserModel;
import com.example.shop_server.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


    @Service
    public class OrderService {

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private OrderDetailRepository orderDetailRepository;

        @Autowired
        private ProductModelRepository productModelRepository;

        @Autowired
        UserService userService;

        public OrderModel findCart(int userId) {
            // Tìm OrderModel với status là SHOPPING của user
            return orderRepository.findByUserIdAndStatus(userId, String.valueOf(OrderEnum.SHOPPING));
        }


        public void addToCart(int userId, AddToCard body) {
            try {
                OrderModel cart = this.findCart(userId);
                Optional<UserModel> user = userService.findById(userId);
                Optional<ProductModel> productModel = productModelRepository.findById(body.getProduct());

                if(cart == null) {
                    // Tạo giỏ hàng mới
                    OrderModel newCart = new OrderModel();
                    if (user.isPresent()) {
                        newCart.setUser(user.get());
                        newCart.setNote("Không ghi chú gì");
                        newCart.setCreateDate(new Date().toString());
                        newCart.setUpdateDate(new Date().toString());
                        newCart.setStatus(OrderEnum.SHOPPING);

                        cart = orderRepository.save(newCart);

                        addOrUpdateOrderDetail(cart, productModel, body.getQuantity(), body.getPrice());
                    } else {
                        throw new RuntimeException("User not found");
                    }
                } else {
                    // Giỏ hàng đã tồn tại
                    addOrUpdateOrderDetail(cart, productModel, body.getQuantity(), body.getPrice());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error adding to cart: " + e.getMessage());
            }
        }

        private void addOrUpdateOrderDetail(OrderModel cart, Optional<ProductModel> productModel, int quantity, double price) {
            if(productModel.isPresent()) {
                OrderDetailModel existingDetail = orderDetailRepository.findItem(cart.getId(), productModel.get().getId());

                if(existingDetail != null) {
                    // Cập nhật số lượng nếu sản phẩm đã tồn tại trong giỏ hàng
                    existingDetail.setQuantity(existingDetail.getQuantity() + quantity);
                    orderDetailRepository.save(existingDetail);
                } else {
                    // Thêm mới nếu sản phẩm chưa có trong giỏ hàng
                    OrderDetailModel newDetail = new OrderDetailModel();
                    newDetail.setOrders(cart);
                    newDetail.setProductmodel(productModel.get());
                    newDetail.setPrice(price);
                    newDetail.setQuantity(quantity);
                    orderDetailRepository.save(newDetail);
                }
                cart.setTotalPrices(cart.getTotalPrices() + (quantity * price));
                orderRepository.save(cart);
            } else {
                throw new RuntimeException("Product model not found");
            }
        }
        public void checkOut(int orderId) {
            Optional<OrderModel> order = orderRepository.findById(orderId);
            if(order.isPresent()) {
                order.get().setStatus(OrderEnum.WAITING);
                orderRepository.save(order.get());
            }
        }

        public List<OrderModel> findByUserId(int userId) {
            return orderRepository.findByUserId(userId);
        }
    }

