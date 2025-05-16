package com.dadry.orderservice.controller;

import com.dadry.orderservice.domain.response.MessageResponse;
import com.dadry.orderservice.dto.OrderDTO;
import com.dadry.orderservice.dto.SaveOrderDTO;
import com.dadry.orderservice.model.Order;
import com.dadry.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody SaveOrderDTO orderDTO) {
        orderService.save(orderDTO);

        return new ResponseEntity<>(new MessageResponse("Order has been created successfully"), HttpStatus.CREATED);
    }

}
