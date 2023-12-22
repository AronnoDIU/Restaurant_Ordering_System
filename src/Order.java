import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

class Order {
    private final List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.calculateItemTotal();
        }
        return total;
    }

    public void displayOrderSummary() {
        System.out.println("Order Summary:");
        for (OrderItem item : items) {
            System.out.println(item.quantity() + "x " + item.menuItem().name() +
                    " - $" + item.calculateItemTotal());
        }
        System.out.println("Total: $" + calculateTotal());
    }
}

// Or,

/*
class Order {
    private final Map<MenuItem, Integer> items = new HashMap<>();

    public void addItem(MenuItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().price() * entry.getValue();
        }
        return total;
    }

    public void displayOrderSummary() {
        System.out.println("Order Summary:");
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey().name() + " x" +
                    entry.getValue() + " - $" + entry.getKey().price() * entry.getValue());
        }
        System.out.println("Total: $" + calculateTotal());
    }
}*/
