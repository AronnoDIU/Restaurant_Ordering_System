import java.util.HashMap;
import java.util.Map;

class UserManager {
    private static final Map<String, User> users = new HashMap<>();

    public static void addUser(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password));
            System.out.println("User created successfully. Please login.");
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }

    public static User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            return user;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return null;
        }
    }
}