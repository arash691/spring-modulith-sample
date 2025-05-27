package com.arash.ariani.order;

import com.arash.ariani.inventory.InventoryService;
import com.arash.ariani.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.modulith.events.ApplicationModuleEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final PaymentService paymentService;

    @Transactional
    public Order createOrder(String customerId, Long productId, Integer quantity) {
        // Reserve inventory
        inventoryService.reserveStock(productId, quantity);

        // Create order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.CREATED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.PROCESSING);
        return orderRepository.save(order);
    }

    @Transactional
    public Order completeOrder(Long orderId, Double amount, String paymentMethod) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Process payment
        paymentService.processPayment(orderId, amount, paymentMethod);
        
        order.setStatus(Order.OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Refund payment if exists
        try {
            paymentService.refundPayment(orderId);
        } catch (Exception e) {
            // Log payment refund failure
            System.out.println("Payment refund failed: " + e.getMessage());
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @ApplicationModuleListener
    void on(ApplicationModuleEvent event) {
        if (event.getPayload() instanceof com.arash.ariani.inventory.InventoryEvent inventoryEvent) {
            System.out.println("Order module received inventory event: " + inventoryEvent);
        } else if (event.getPayload() instanceof com.arash.ariani.payment.PaymentEvent paymentEvent) {
            System.out.println("Order module received payment event: " + paymentEvent);
        }
    }
} 