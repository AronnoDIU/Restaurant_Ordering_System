import java.io.Serial;

class MenuItem implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name; // The name of the menu item
    private double price; // The price of the menu item

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return String.format("%s ($%.2f)", name, price);
    }
}
