package com.example.shop_server.modules.orderDetail.service;

import com.example.shop_server.modules.orderDetail.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

}
