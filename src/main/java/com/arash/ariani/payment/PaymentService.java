package com.arash.ariani.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Payment processPayment(Long orderId, Double amount, String paymentMethod) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);
        eventPublisher.publishEvent(new PaymentEvent(orderId, amount, PaymentEvent.EventType.PAYMENT_INITIATED));
        
        // Simulate payment processing
        try {
            Thread.sleep(1000); // Simulate payment processing time
            savedPayment.setStatus(Payment.PaymentStatus.COMPLETED);
            savedPayment = paymentRepository.save(savedPayment);
            eventPublisher.publishEvent(new PaymentEvent(orderId, amount, PaymentEvent.EventType.PAYMENT_COMPLETED));
        } catch (Exception e) {
            savedPayment.setStatus(Payment.PaymentStatus.FAILED);
            savedPayment = paymentRepository.save(savedPayment);
            eventPublisher.publishEvent(new PaymentEvent(orderId, amount, PaymentEvent.EventType.PAYMENT_FAILED));
        }

        return savedPayment;
    }

    @Transactional
    public Payment refundPayment(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        Payment savedPayment = paymentRepository.save(payment);
        eventPublisher.publishEvent(new PaymentEvent(orderId, payment.getAmount(), PaymentEvent.EventType.PAYMENT_REFUNDED));

        return savedPayment;
    }

    @EventListener
    void onPaymentEvent(PaymentEvent paymentEvent) {
        System.out.println("Payment event received: " + paymentEvent);
    }
} 