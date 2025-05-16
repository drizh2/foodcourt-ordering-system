package com.dadry.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDTO {
    private String customerName;
    private LocalDateTime orderDate;
    private String content;
}
