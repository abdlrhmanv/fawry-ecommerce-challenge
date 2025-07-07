import java.util.ArrayList;

public class Cart {
    ArrayList<Product> items = new ArrayList<>();

    public void add(Product product, int qty) {
        if (qty > product.quantity) {
            System.out.println("Cannot add, not enough stock for " + product.name);
            return;
        }
        product.quantity -= qty;
        
        // Preserve the original product type when adding to cart
        if (product instanceof ShippableProduct) {
            ShippableProduct shippableProduct = (ShippableProduct) product;
            items.add(new ShippableProduct(shippableProduct.name, shippableProduct.price, qty, shippableProduct.weight));
        } else if (product instanceof ExpirableProduct) {
            ExpirableProduct expirableProduct = (ExpirableProduct) product;
            items.add(new ExpirableProduct(expirableProduct.name, expirableProduct.price, qty, expirableProduct.expirationDate));
        } else {
            items.add(new Product(product.name, product.price, qty));
        }
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
