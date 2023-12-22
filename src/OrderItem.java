record OrderItem(MenuItem menuItem, int quantity) {

    public double calculateItemTotal() {
        return menuItem.price() * quantity;
    }
}