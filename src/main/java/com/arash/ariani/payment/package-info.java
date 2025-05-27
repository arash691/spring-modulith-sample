/**
 * The Payment module handles payment processing and transaction management.
 * 
 * This module is responsible for:
 * - Payment processing
 * - Payment status tracking
 * - Refund handling
 * 
 * Dependencies:
 * - None (self-contained module)
 * 
 * Events:
 * - Publishes payment initiated events
 * - Publishes payment completed events
 * - Publishes payment failed events
 * - Publishes payment refunded events
 */
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {}
)
package com.arash.ariani.payment; 