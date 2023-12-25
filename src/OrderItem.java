import java.io.Serial;

class OrderItem implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    MenuItem menuItem;
    int quantity;
    public String note;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.note = "";
    }

    public double getTotal() {
        return menuItem.getPrice() * quantity;
    }
}