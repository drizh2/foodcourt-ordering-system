package com.dadry.orderservice.service;

import com.dadry.orderservice.dto.FullOrderDTO;
import com.dadry.orderservice.dto.OrderDTO;
import com.dadry.orderservice.dto.SaveOrderDTO;
import com.dadry.orderservice.model.Order;
import com.dadry.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${kafka.topic.orderCreated}")
    private String orderCreated;

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, FullOrderDTO> kafkaTemplate;

    public Order save(SaveOrderDTO orderDTO) {
        Order order = Order.builder()
                .content(orderDTO.getContent())
                .customerName(orderDTO.getCustomerName())
                .orderDate(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        FullOrderDTO orderMessage = mapOrderToFullOrderDTO(order);
        kafkaTemplate.send(orderCreated, orderMessage);

        return order;
    }

    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return null;
        } else {
            return mapOrderToOrderDTO(order);
        }
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::mapOrderToOrderDTO)
                .toList();
    }

    public OrderDTO mapOrderToOrderDTO(Order order) {
        return OrderDTO.builder()
                .content(order.getContent())
                .customerName(order.getCustomerName())
                .orderDate(order.getOrderDate())
                .build();
    }

    private FullOrderDTO mapOrderToFullOrderDTO(Order order) {
        return FullOrderDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .orderDate(order.getOrderDate())
                .content(order.getContent())
                .build();
    }

}
