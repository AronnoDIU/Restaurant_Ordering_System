import java.util.ArrayList;
import java.util.List;

class User {
    private final String username;
    private final String password;
    private final List<Order> orderHistory = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }
}