public class RestaurantOrderingSystem {
    public static void main(String[] args) {
        Order order = new Order();
        order.addItem(new MenuItem("Burger", 5.99));
        order.addItem(new MenuItem("Fries", 2.99));
        order.addItem(new MenuItem("Soda", 1.99));
        order.displayOrderSummary();
    }
}
