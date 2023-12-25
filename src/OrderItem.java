import java.io.Serial;

class OrderItem implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    MenuItem menuItem; // The menu item that was ordered
    int quantity; // The number of that menu item that was ordered
    public String note; // Any special requests for the menu item

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.note = "";
    }

    // Returns the total price of the order item
    public double getTotal() {
        return menuItem.getPrice() * quantity;
    }
}