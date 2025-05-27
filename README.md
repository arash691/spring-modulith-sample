# Spring Modulith Sample Project

This project demonstrates the usage of Spring Modulith, a framework for building modular applications with Spring Boot. The sample implements a multi-module order management system with inventory and payment processing capabilities.

## Project Structure

The project follows a modular architecture with three main modules:

```
src/main/java/com/arash/ariani/
├── SpringModulithSampleApplication.java
├── order/
│   ├── Order.java
│   ├── OrderRepository.java
│   ├── OrderService.java
│   └── OrderController.java
├── inventory/
│   ├── Product.java
│   ├── ProductRepository.java
│   ├── InventoryService.java
│   ├── InventoryEvent.java
│   └── InventoryController.java
└── payment/
    ├── Payment.java
    ├── PaymentRepository.java
    ├── PaymentService.java
    ├── PaymentEvent.java
    └── PaymentController.java
```

## Key Features

1. **Modular Architecture**: The application is organized into three distinct modules:
   - Order Management
   - Inventory Management
   - Payment Processing

2. **Event-Driven Communication**: Modules communicate through events:
   - Inventory events for stock updates
   - Payment events for transaction status
   - Order events for order lifecycle

3. **Persistence**: Uses JPA for data persistence with H2 database
4. **REST API**: Exposes REST endpoints for all modules

## API Endpoints

### Order Management
- `POST /api/orders?customerId={id}&productId={id}&quantity={qty}` - Create a new order
- `PUT /api/orders/{orderId}/process` - Process an order
- `PUT /api/orders/{orderId}/complete?amount={amount}&paymentMethod={method}` - Complete an order
- `PUT /api/orders/{orderId}/cancel` - Cancel an order

### Inventory Management
- `POST /api/inventory/products` - Create a new product
- `PUT /api/inventory/products/{productId}/stock?quantity={qty}` - Update stock
- `PUT /api/inventory/products/{productId}/reserve?quantity={qty}` - Reserve stock

### Payment Processing
- `POST /api/payments/orders/{orderId}?amount={amount}&paymentMethod={method}` - Process payment
- `PUT /api/payments/orders/{orderId}/refund` - Refund payment

## Module Communication

The modules communicate through events:

1. **Order Module**:
   - Listens to Inventory and Payment events
   - Coordinates the order lifecycle

2. **Inventory Module**:
   - Publishes stock update events
   - Handles stock reservations

3. **Payment Module**:
   - Publishes payment status events
   - Handles payment processing and refunds

## Getting Started

1. Clone the repository
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Dependencies

- Spring Boot 3.2.3
- Spring Modulith 1.1.3
- Spring Data JPA
- H2 Database
- Lombok

## Benefits of Spring Modulith

1. **Clear Module Boundaries**: Enforces clear boundaries between different parts of the application
2. **Improved Maintainability**: Easier to understand and maintain due to modular structure
3. **Better Testability**: Modules can be tested in isolation
4. **Event-Driven Architecture**: Promotes loose coupling through events
5. **Documentation**: Automatic documentation of module structure and dependencies

## Best Practices

1. Keep modules focused on specific business capabilities
2. Use events for inter-module communication
3. Maintain clear boundaries between modules
4. Document module interfaces and events
5. Write tests for each module independently

## Contributing

Feel free to submit issues and enhancement requests! 