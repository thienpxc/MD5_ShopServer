package com.example.shop_server.modules.order;


import com.example.shop_server.modules.jwt.JwtService;
import com.example.shop_server.modules.order.dto.req.AddToCard;
import com.example.shop_server.modules.order.service.OrderService;
import com.example.shop_server.modules.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestHeader("token") String token, @RequestBody AddToCard body) {
        try {
            UserModel user = JwtService.verifyTokenUser(token);
            orderService.addToCart(user.getId(), body);
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/order/{id}/checkout")
    public ResponseEntity<?> checkoutOrder(@RequestHeader("token") String token, @PathVariable("id") int id) {
        try {

            UserModel user = JwtService.verifyTokenUser(token);
            orderService.checkOut(id);
            return ResponseEntity.ok("Order checked out successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/order/find-cart")
    public List<OrderModel> findCart(@RequestHeader("token") String token) {
        try {
            UserModel user = JwtService.verifyTokenUser(token);

            return orderService.findByUserId(user.getId());
        }catch (Exception e){
            return null;
        }
    }



}
