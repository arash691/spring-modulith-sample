package com.arash.ariani.inventory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InventoryEvent {
    private final Long productId;
    private final Integer quantity;
    private final EventType type;

    public enum EventType {
        STOCK_UPDATED,
        STOCK_DEPLETED,
        STOCK_RESERVED
    }
} 