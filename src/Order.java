import java.io.Serial;
import java.util.ArrayList; // For storing menu items, users, and orders

class Order implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    static int orderIdCounter = 1;
    int orderId; // The order ID
    ArrayList<OrderItem> items; // The items in the order

    public Order() {
        this.orderId = orderIdCounter++; // Set the order ID and increment the counter
        this.items = new ArrayList<>(); // Initialize the item
    }

    // Adds an item to the order list of items
    public void addItem(OrderItem item) {
        items.add(item);
    }

    // Calculates the total price of the order by summing the total price of each item
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
