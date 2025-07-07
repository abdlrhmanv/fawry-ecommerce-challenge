public class ExpirableProduct extends Product {
    String expirationDate;

    public ExpirableProduct(String name, double price, int quantity, String expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }
}

