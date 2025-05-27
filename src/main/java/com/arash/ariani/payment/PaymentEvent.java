package com.arash.ariani.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentEvent {
    private Long orderId;
    private Double amount;
    private EventType eventType;

    public enum EventType {
        PAYMENT_INITIATED,
        PAYMENT_COMPLETED,
        PAYMENT_FAILED,
        PAYMENT_REFUNDED
    }
} 