package com.Spring.microservices.order.service;

import com.Spring.microservices.order.dto.OrderRequest;
import com.Spring.microservices.order.model.Order;
import com.Spring.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderRequest orderRequest;
    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.getPrice());
        order.setSkuCode(orderRequest.getSkuCode());
        order.setQuantity(orderRequest.getQuantity());

        orderRepository.save(order);

    }
}
