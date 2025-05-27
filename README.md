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
│   ├── OrderController.java
│   └── package-info.java
├── inventory/
│   ├── Product.java
│   ├── ProductRepository.java
│   ├── InventoryService.java
│   ├── InventoryEvent.java
│   ├── InventoryController.java
│   └── package-info.java
└── payment/
    ├── Payment.java
    ├── PaymentRepository.java
    ├── PaymentService.java
    ├── PaymentEvent.java
    ├── PaymentController.java
    └── package-info.java
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

## API Documentation

### Order Management
- `POST /api/orders`
  - Create a new order
  - Parameters:
    - `customerId` (String): Customer identifier
    - `productId` (Long): Product identifier
    - `quantity` (Integer): Order quantity
  - Returns: Created order details

- `PUT /api/orders/{orderId}/process`
  - Process an order
  - Parameters:
    - `orderId` (Long): Order identifier
  - Returns: Updated order details

- `PUT /api/orders/{orderId}/complete`
  - Complete an order with payment
  - Parameters:
    - `orderId` (Long): Order identifier
    - `amount` (Double): Payment amount
    - `paymentMethod` (String): Payment method
  - Returns: Completed order details

- `PUT /api/orders/{orderId}/cancel`
  - Cancel an order
  - Parameters:
    - `orderId` (Long): Order identifier
  - Returns: Cancelled order details

### Inventory Management
- `POST /api/inventory/products`
  - Create a new product
  - Body: Product details (name, description, quantity, price)
  - Returns: Created product details

- `GET /api/inventory/products/{productId}`
  - Get product details
  - Parameters:
    - `productId` (Long): Product identifier
  - Returns: Product details

- `PUT /api/inventory/products/{productId}/stock`
  - Update product stock
  - Parameters:
    - `productId` (Long): Product identifier
    - `quantity` (Integer): Stock quantity to add/remove
  - Returns: Updated product details

- `PUT /api/inventory/products/{productId}/reserve`
  - Reserve stock for an order
  - Parameters:
    - `productId` (Long): Product identifier
    - `quantity` (Integer): Quantity to reserve
  - Returns: Updated product details

### Payment Processing
- `POST /api/payments/orders/{orderId}`
  - Process payment for an order
  - Parameters:
    - `orderId` (Long): Order identifier
    - `amount` (Double): Payment amount
    - `paymentMethod` (String): Payment method
  - Returns: Payment details

- `PUT /api/payments/orders/{orderId}/refund`
  - Refund payment for an order
  - Parameters:
    - `orderId` (Long): Order identifier
  - Returns: Refund details

## Module Communication

The modules communicate through events:

1. **Order Module**:
   - Listens to Inventory and Payment events
   - Coordinates the order lifecycle
   - Dependencies: Inventory and Payment modules

2. **Inventory Module**:
   - Publishes stock update events
   - Handles stock reservations
   - Self-contained (no dependencies)

3. **Payment Module**:
   - Publishes payment status events
   - Handles payment processing and refunds
   - Self-contained (no dependencies)

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

## Example API Usage

1. Create a product:
```bash
curl -X POST http://localhost:8080/api/inventory/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Product","description":"Test Description","quantity":100,"price":99.99}'
```

2. Create an order:
```bash
curl -X POST "http://localhost:8080/api/orders?customerId=CUST001&productId=1&quantity=2"
```

3. Process the order:
```bash
curl -X PUT http://localhost:8080/api/orders/1/process
```

4. Complete the order with payment:
```bash
curl -X PUT "http://localhost:8080/api/orders/1/complete?amount=199.98&paymentMethod=CREDIT_CARD"
```

## Dependencies

- Spring Boot 3.4.5
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