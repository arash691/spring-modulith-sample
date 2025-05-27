/**
 * The Inventory module manages product stock and inventory operations.
 * 
 * This module is responsible for:
 * - Product management
 * - Stock level tracking
 * - Stock reservation and updates
 * 
 * Dependencies:
 * - None (self-contained module)
 * 
 * Events:
 * - Publishes stock update events
 * - Publishes stock depletion events
 * - Publishes stock reservation events
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {}
)
package com.arash.ariani.inventory; 