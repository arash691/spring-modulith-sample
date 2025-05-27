package com.arash.ariani.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String customerId;
    private LocalDateTime createdAt;
    private OrderStatus status;

    public enum OrderStatus {
        CREATED, PROCESSING, COMPLETED, CANCELLED
    }
} 