/**
 * The Order module handles the core order management functionality.
 * 
 * This module is responsible for:
 * - Order creation and lifecycle management
 * - Order status tracking
 * - Coordination with Inventory and Payment modules
 * 
 * Dependencies:
 * - Inventory module (for stock reservation)
 * - Payment module (for payment processing)
 * 
 * Events:
 * - Listens to Inventory events for stock updates
 * - Listens to Payment events for payment status
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {
        "inventory",
        "payment"
    }
)
package com.arash.ariani.order; 