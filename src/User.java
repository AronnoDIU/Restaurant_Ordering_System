import java.io.Serial; // For serializing and deserializing objects to and from files
import java.util.ArrayList; // For storing menu items, users, and orders
import java.util.List; // For storing menu items, users, and orders

class User implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    String username;
    String password;
    List<Order> orders;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.orders = new ArrayList<>();
    }
}