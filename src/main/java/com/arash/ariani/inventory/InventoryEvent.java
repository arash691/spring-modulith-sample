package com.arash.ariani.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryEvent {
    private Long productId;
    private Integer quantity;
    private EventType eventType;

    public enum EventType {
        STOCK_DEPLETED, STOCK_UPDATED, STOCK_RESERVED
    }
} 