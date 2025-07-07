# E-Commerce Cart System

## Overview
A Java-based e-commerce system that manages products, shopping cart operations, and checkout processes with support for shippable and expirable products.

## Features

### Core Product Management
- **Product Definition**: Products with name, price, and quantity
- **Expirable Products**: Products with expiration dates (e.g., Cheese, Biscuits)
- **Shippable Products**: Products requiring shipping with weight specifications (e.g., TV, Laptop)
- **Digital Products**: Non-shippable products (e.g., Gift Cards, Software)

### Shopping Cart Operations
- Add products to cart with quantity validation
- Automatic stock management
- Support for mixed product types in single cart
- Preserve product type information (shippable/expirable)

### Checkout Process
- **Order Calculations**:
  - Subtotal (sum of all items' prices)
  - Shipping fees (30 units for shippable items)
  - Total amount (subtotal + shipping fees)
  - Customer balance update

- **Validation & Error Handling**:
  - Empty cart detection
  - Insufficient customer balance
  - Out of stock products
  - Expired product detection
  - Stock quantity validation

### Shipping Integration
- **ShippingService**: Handles all shippable items
- **Shipment Notice**: Displays items with weights and total package weight
- **Shippable Interface**: Standardized interface for shippable items (`getName()`, `getWeight()`)

## System Architecture

### Class Structure

```
Product (Base Class)
├── ExpirableProduct
└── ShippableProduct (implements Shippable)

Cart
├── Manages ArrayList<Product>
├── Validates stock and quantities
└── Preserves product types

Customer
├── Name and balance management
└── Transaction processing

ShippingService
├── Processes Shippable items
├── Calculates total weight
└── Generates shipment notices

Shippable Interface
├── getName(): String
└── getWeight(): double
```

### Key Design Decisions

1. **Type Preservation**: Cart maintains original product types (ShippableProduct, ExpirableProduct)
2. **Interface Segregation**: Shippable interface separates shipping concerns
3. **Single Responsibility**: Each class has a focused purpose
4. **Extensibility**: Easy to add new product types

## Installation & Usage

### Prerequisites
- Java 8 or higher
- Java compiler (javac)

### Running the System

```bash
# Compile all Java files
javac *.java

# Run the main application
java Main
```

### Code Example

```java
// Create customer
Customer customer = new Customer("Ahmed", 1000);

// Create products
ShippableProduct cheese = new ShippableProduct("Cheese", 100, 5, 0.4);
ShippableProduct tv = new ShippableProduct("TV", 2000, 2, 15.0);
Product scratchCard = new Product("ScratchCard", 50, 10);

// Shopping cart operations
Cart cart = new Cart();
cart.add(cheese, 2);
cart.add(tv, 1);
cart.add(scratchCard, 1);

// Checkout process
checkout(customer, cart);
```

## Expected Output

### Shipment Notice
```
** Shipment notice **
2x Cheese 400g
1x Biscuits 700g
Total package weight 1.1kg
```

### Checkout Receipt
```
** Checkout receipt **
2x Cheese 200
1x Biscuits 150
----------------------
Subtotal 350
Shipping 30
Amount 380
```

## Test Cases Covered

### 1. Mixed Products (Shippable + Non-shippable)
- Demonstrates complete checkout flow
- Shows shipment notice and receipt

### 2. Shippable Items Only
- Tests shipping service integration
- Validates weight calculations

### 3. Digital Products Only
- No shipping required
- Tests non-shippable flow

### 4. Error Scenarios
- **Empty Cart**: Prevents checkout of empty cart
- **Insufficient Balance**: Validates customer funds
- **Out of Stock**: Prevents over-ordering
- **Expired Products**: Blocks checkout of expired items

### 5. Edge Cases
- Stock depletion handling
- Multiple quantities of same product
- Weight aggregation for shipping

## Assumptions Made

### Date Format
- Expiration dates use "YYYY-MM-DD" format
- Products dated "2023-01-01" or earlier are considered expired

### Shipping Policy
- Fixed shipping fee of 30 units regardless of weight
- Shipping applies when cart contains any shippable items
- Weight displayed in grams, total weight in kilograms

### Currency
- All prices are in generic units (no specific currency)
- Integer display for monetary values in receipts

### Stock Management
- Products maintain available quantity
- Cart operations reduce product stock
- No restocking mechanism implemented

## File Structure

```
├── Main.java              # Main application and test cases
├── Product.java           # Base product class
├── ExpirableProduct.java  # Products with expiration dates
├── ShippableProduct.java  # Products requiring shipping
├── Shippable.java         # Interface for shippable items
├── Cart.java              # Shopping cart management
├── Customer.java          # Customer data and balance
├── ShippingService.java   # Shipping processing service
└── README.md              # Project documentation
```

## Future Enhancements

### Potential Improvements
1. **Database Integration**: Persistent product and customer data
2. **Advanced Date Handling**: Real-time expiration checking
3. **Dynamic Shipping**: Weight-based shipping calculations
4. **Inventory Management**: Automated restocking
5. **Payment Gateway**: Multiple payment methods
6. **User Interface**: Web or desktop GUI
7. **Reporting**: Sales and inventory reports

### Scalability Considerations
- **Product Catalog**: Support for categories and search
- **Multi-currency**: International support
- **Multi-warehouse**: Distributed inventory
- **Promotions**: Discount and coupon system

## Technical Notes

### Object-Oriented Principles Applied
- **Inheritance**: Product hierarchy
- **Polymorphism**: Mixed product types in cart
- **Encapsulation**: Private fields with public methods
- **Interface Implementation**: Shippable contract

### Error Handling Strategy
- **Validation at Entry**: Prevent invalid operations
- **Graceful Degradation**: Continue operation when possible
- **Clear Messaging**: Informative error messages
- **Early Return**: Fail fast on critical errors

## Contact & Support

This project was developed as part of the Fawry Quantum Internship Challenge. For questions or support, please refer to the project documentation or contact the development team.

---

**Note**: This implementation focuses on core functionality and educational purposes. Production deployment would require additional security, validation, and performance considerations. 