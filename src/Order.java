import java.util.HashMap;
import java.util.Map;

class Order {
    private final Map<MenuItem, Integer> items = new HashMap<>();

    public void addItem(MenuItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void displayOrderSummary() {
        System.out.println("Order Summary:");
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey().getName() + " x" + entry.getValue() + " - $" + entry.getKey().getPrice() * entry.getValue());
        }
        System.out.println("Total: $" + calculateTotal());
    }
}