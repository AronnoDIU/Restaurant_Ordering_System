record OrderItem(MenuItem menuItem, int quantity) {

    public double calculateItemTotal() {
        return menuItem.price() * quantity;
    }
}

// Or,

/*class OrderItem {
    private final MenuItem menuItem;
    private final int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double calculateItemTotal() {
        return menuItem.getPrice() * quantity;
    }
}*/
