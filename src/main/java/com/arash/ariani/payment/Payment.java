package com.arash.ariani.payment;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Double amount;
    private String paymentMethod;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }
} 