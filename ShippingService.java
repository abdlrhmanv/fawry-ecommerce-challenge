import java.util.ArrayList;
import java.util.HashMap;

public class ShippingService {
    public void shipItems(ArrayList<Shippable> items) {
        HashMap<String, Integer> counts = new HashMap<>();
        HashMap<String, Double> weights = new HashMap<>();

        double totalWeight = 0;

        for (Shippable item : items) {
            String name = item.getName();
            counts.put(name, counts.getOrDefault(name, 0) + 1);
            weights.put(name, item.getWeight());
            totalWeight += item.getWeight();
        }

        System.out.println("** Shipment notice **");
        for (String name : counts.keySet()) {
            int qty = counts.get(name);
            double weight = weights.get(name) * 1000; // تحويل الى جرام
            System.out.printf("%dx %s %.0fg\n", qty, name, weight);
        }
        System.out.printf("Total package weight %.1fkg\n\n", totalWeight);
    }
}
