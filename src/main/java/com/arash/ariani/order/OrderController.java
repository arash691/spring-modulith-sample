package com.arash.ariani.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam String customerId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(orderService.createOrder(customerId, productId, quantity));
    }

    @PutMapping("/{orderId}/process")
    public ResponseEntity<Order> processOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.processOrder(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<Order> completeOrder(
            @PathVariable Long orderId,
            @RequestParam Double amount,
            @RequestParam String paymentMethod) {
        return ResponseEntity.ok(orderService.completeOrder(orderId, amount, paymentMethod));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
} 