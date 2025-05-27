package com.arash.ariani.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product updateStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setQuantity(product.getQuantity() + quantity);
        Product savedProduct = productRepository.save(product);

        // Publish event based on stock level
        if (savedProduct.getQuantity() <= 0) {
            eventPublisher.publishEvent(new InventoryEvent(productId, savedProduct.getQuantity(), 
                InventoryEvent.EventType.STOCK_DEPLETED));
        } else {
            eventPublisher.publishEvent(new InventoryEvent(productId, savedProduct.getQuantity(), 
                InventoryEvent.EventType.STOCK_UPDATED));
        }

        return savedProduct;
    }

    @Transactional
    public Product reserveStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - quantity);
        Product savedProduct = productRepository.save(product);

        eventPublisher.publishEvent(new InventoryEvent(productId, quantity, 
            InventoryEvent.EventType.STOCK_RESERVED));

        return savedProduct;
    }

    @EventListener
    void onInventoryEvent(InventoryEvent inventoryEvent) {
        System.out.println("Inventory event received: " + inventoryEvent);
    }
} 