package com.Spring.microservices.order.service;

import com.Spring.microservices.order.client.InventoryClient;
import com.Spring.microservices.order.dto.OrderRequest;
import com.Spring.microservices.order.model.Order;
import com.Spring.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInstock(orderRequest.getSkuCode(),orderRequest.getQuantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.getPrice());
            order.setSkuCode(orderRequest.getSkuCode());
            order.setQuantity(orderRequest.getQuantity());

            orderRepository.save(order);
        }
        else {throw new RuntimeException("Product with skuCode"+orderRequest.getSkuCode()+"is not in Stock");
    }
    }
}
