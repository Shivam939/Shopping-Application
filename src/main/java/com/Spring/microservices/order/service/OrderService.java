package com.Spring.microservices.order.service;

import com.Spring.microservices.order.client.InventoryClient;
import com.Spring.microservices.order.dto.OrderRequest;
import com.Spring.microservices.order.event.OrderPlacedEvent;
import com.Spring.microservices.order.model.Order;
import com.Spring.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInstock(orderRequest.skuCode(),orderRequest.quantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);

           //Send the message to Kafka Topic

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
            log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic Order-Placed",orderPlacedEvent);
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            log.info("End- Sending OrderPlacedEvent {} to Kafka Topic Order-Placed",orderPlacedEvent);
        }
        else {throw new RuntimeException("Product with skuCode " +orderRequest.skuCode()+ " is not in Stock");
    }
    }
}
