import java.io.Serial;
import java.util.ArrayList;

class Order implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    static int orderIdCounter = 1;
    int orderId;
    ArrayList<OrderItem> items;

    public Order() {
        this.orderId = orderIdCounter++;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}