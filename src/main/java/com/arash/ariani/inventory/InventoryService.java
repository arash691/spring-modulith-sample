package com.arash.ariani.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.modulith.events.ApplicationModuleEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final ProductRepository productRepository;

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
            ApplicationModuleEvent.of(new InventoryEvent(productId, savedProduct.getQuantity(), 
                InventoryEvent.EventType.STOCK_DEPLETED));
        } else {
            ApplicationModuleEvent.of(new InventoryEvent(productId, savedProduct.getQuantity(), 
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

        ApplicationModuleEvent.of(new InventoryEvent(productId, quantity, 
            InventoryEvent.EventType.STOCK_RESERVED));

        return savedProduct;
    }

    @ApplicationModuleListener
    void on(ApplicationModuleEvent event) {
        if (event.getPayload() instanceof InventoryEvent inventoryEvent) {
            System.out.println("Inventory event received: " + inventoryEvent);
        }
    }
} 