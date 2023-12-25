import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

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