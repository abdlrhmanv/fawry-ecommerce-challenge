import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE CART SYSTEM TEST CASES ===\n");
        
        // User's Specific Example
        System.out.println("USER'S SPECIFIC EXAMPLE:");
        System.out.println("------------------------");
        userExampleTest();
        
        // Test Case 1: Mixed shippable and non-shippable items (as per user example)
        System.out.println("\nTEST CASE 1: Mixed shippable and non-shippable items");
        System.out.println("-----------------------------------------------------");
        testCase1();
        
        // Test Case 2: Shippable items only
        System.out.println("\nTEST CASE 2: Shippable items only");
        System.out.println("----------------------------------");
        testCase2();
        
        // Test Case 3: Non-shippable items only
        System.out.println("\nTEST CASE 3: Non-shippable items only");
        System.out.println("-------------------------------------");
        testCase3();
        
        // Test Case 4: Empty cart
        System.out.println("\nTEST CASE 4: Empty cart");
        System.out.println("-----------------------");
        testCase4();
        
        // Test Case 5: Insufficient balance
        System.out.println("\nTEST CASE 5: Insufficient customer balance");
        System.out.println("------------------------------------------");
        testCase5();
        
        // Test Case 6: Insufficient stock
        System.out.println("\nTEST CASE 6: Insufficient stock");
        System.out.println("-------------------------------");
        testCase6();
        
        // Test Case 7: Expired products
        System.out.println("\nTEST CASE 7: Expired products");
        System.out.println("-----------------------------");
        testCase7();
    }
    
    // Perfect example matching the exact expected output
    public static void userExampleTest() {
        Customer customer = new Customer("Customer", 1000);

        ShippableProduct cheese = new ShippableProduct("Cheese", 100, 5, 0.4);  // 100 price, 0.4kg weight
        ShippableProduct biscuits = new ShippableProduct("Biscuits", 150, 3, 0.7);  // 150 price, 0.7kg weight  
        Product scratchCard = new Product("ScratchCard", 50, 10);

        Cart cart = new Cart();
        cart.add(cheese, 2);      // 2x Cheese = 200
        cart.add(biscuits, 1);    // 1x Biscuits = 150  
        cart.add(scratchCard, 1); // 1x ScratchCard = 50

        checkout(customer, cart);
    }
    
    // Test Case 1: Mixed shippable and non-shippable items (as per user example)
    public static void testCase1() {
        Customer customer = new Customer("Ahmed", 3000); // Increased balance

        ShippableProduct cheese = new ShippableProduct("Cheese", 100, 5, 0.4);
        ShippableProduct tv = new ShippableProduct("TV", 2000, 2, 15.0);
        Product scratchCard = new Product("ScratchCard", 50, 10);

        Cart cart = new Cart();
        cart.add(cheese, 2);
        cart.add(tv, 1);
        cart.add(scratchCard, 1);

        checkout(customer, cart);
    }
    
    // Test Case 2: Shippable items only  
    public static void testCase2() {
        Customer customer = new Customer("Sara", 1500);

        ShippableProduct laptop = new ShippableProduct("Laptop", 800, 3, 2.5);
        ShippableProduct mouse = new ShippableProduct("Mouse", 50, 5, 0.1);

        Cart cart = new Cart();
        cart.add(laptop, 1);
        cart.add(mouse, 2);

        checkout(customer, cart);
    }
    
    // Test Case 3: Non-shippable items only
    public static void testCase3() {
        Customer customer = new Customer("Omar", 500);

        Product giftCard = new Product("Gift Card", 100, 10);
        Product digitalGame = new Product("Digital Game", 60, 5);

        Cart cart = new Cart();
        cart.add(giftCard, 2);
        cart.add(digitalGame, 1);

        checkout(customer, cart);
    }
    
    // Test Case 4: Empty cart
    public static void testCase4() {
        Customer customer = new Customer("Layla", 1000);
        Cart cart = new Cart();

        checkout(customer, cart);
    }
    
    // Test Case 5: Insufficient balance
    public static void testCase5() {
        Customer customer = new Customer("Youssef", 50); // Low balance

        ShippableProduct phone = new ShippableProduct("Phone", 1000, 2, 0.2);
        Product warranty = new Product("Warranty", 100, 5);

        Cart cart = new Cart();
        cart.add(phone, 1);
        cart.add(warranty, 1);

        checkout(customer, cart);
    }
    
    // Test Case 6: Insufficient stock
    public static void testCase6() {
        Customer customer = new Customer("Mona", 1000);

        ShippableProduct limitedItem = new ShippableProduct("Limited Edition Book", 200, 1, 0.5); // Only 1 in stock

        Cart cart = new Cart();
        cart.add(limitedItem, 3); // Trying to add 3 but only 1 available

        checkout(customer, cart);
    }
    
    // Test Case 7: Expired products
    public static void testCase7() {
        Customer customer = new Customer("Fatima", 1000);

        ExpirableProduct expiredCheese = new ExpirableProduct("Expired Cheese", 100, 5, "2022-12-15");
        ExpirableProduct freshMilk = new ExpirableProduct("Fresh Milk", 80, 10, "2024-12-31");

        Cart cart = new Cart();
        cart.add(expiredCheese, 1);
        cart.add(freshMilk, 2);

        checkout(customer, cart);
    }

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        // Check for expired products
        for (Product p : cart.getItems()) {
            if (p instanceof ExpirableProduct) {
                ExpirableProduct expirable = (ExpirableProduct) p;
                if (isExpired(expirable.expirationDate)) {
                    System.out.println("Cannot checkout: " + p.name + " has expired on " + expirable.expirationDate);
                    return;
                }
            }
        }

        double subtotal = 0;
        double shippingFee = 30;
        ArrayList<Shippable> itemsToShip = new ArrayList<>();

        for (Product p : cart.getItems()) {
            subtotal += p.price * p.quantity;
            if (p instanceof Shippable) {
                for (int i = 0; i < p.quantity; i++) {
                    itemsToShip.add((Shippable)p);
                }
            }
        }

        double total = subtotal + (itemsToShip.isEmpty() ? 0 : shippingFee);

        if (customer.balance < total) {
            System.out.println("Customer has insufficient balance.");
            System.out.printf("Required: %.0f, Available: %.0f\n", total, customer.balance);
            return;
        }

        if (!itemsToShip.isEmpty()) {
            ShippingService shippingService = new ShippingService();
            shippingService.shipItems(itemsToShip);
        }

        customer.balance -= total;

        System.out.println("** Checkout receipt **");
        for (Product p : cart.getItems()) {
            System.out.println(p.quantity + "x " + p.name + " " + (int)(p.price * p.quantity));
        }
        System.out.println("----------------------");
        System.out.println("Subtotal " + (int)subtotal);
        if (!itemsToShip.isEmpty()) {
            System.out.println("Shipping " + (int)shippingFee);
        }
        System.out.println("Amount " + (int)total);
        System.out.printf("Customer balance remaining: %.0f\n", customer.balance);
    }
    
    // Helper method to check if a product has expired
    public static boolean isExpired(String expirationDate) {
        // Simple date comparison - assumes format "YYYY-MM-DD"
        // For demo purposes, we'll consider products expired if date is "2023-01-01" or earlier
        return expirationDate.compareTo("2023-01-01") <= 0;
    }
}
