package com.arash.ariani.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<Payment> processPayment(
            @PathVariable Long orderId,
            @RequestParam Double amount,
            @RequestParam String paymentMethod) {
        return ResponseEntity.ok(paymentService.processPayment(orderId, amount, paymentMethod));
    }

    @PutMapping("/orders/{orderId}/refund")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.refundPayment(orderId));
    }
} 