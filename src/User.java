import java.util.ArrayList;
import java.util.List;

class User {
    String username;
    String password;
    List<String> orders;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.orders = new ArrayList<>();
    }
}