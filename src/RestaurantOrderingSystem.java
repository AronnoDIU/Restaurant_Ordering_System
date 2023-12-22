import java.util.*;

public class RestaurantOrderingSystem {
    private static final Map<Integer, MenuItem> menu = new HashMap<>();
    private static final Map<String, User> users = new HashMap<>();
    private static User currentUser;

    public static void main(String[] args) {
        initializeMenu();
        initializeUsers();

        Scanner userInput = new Scanner(System.in);

        // User Authentication
        authenticateUser(userInput);

        // Display Menu
        displayMenu();

        // Place Order
        Order currentOrder = placeOrder(userInput);

        // Display Order Summary
        currentOrder.displayOrderSummary();

        // Update User Order History
        updateUserOrderHistory(currentOrder);

        // Display Order History
        displayOrderHistory();

        // Close Scanner
        userInput.close();
    }

    private static void authenticateUser(Scanner scanner) {
        System.out.println("Welcome to the Restaurant Ordering System!");

        while (true) {
            System.out.print("Enter your username: ");
            String username = scanner.next();

            // Check if the user as a Guest
            if (username.equals("Guest")) {
                System.out.println("Authentication successful. Welcome, Guest!");
                break;
            }

            if (!users.containsKey(username)) {
                System.out.println("Invalid username. Please try again.");
                continue;
            }

            // Check if the user exists
            User user = users.get(username);
            if (user == null) {
                System.out.println("Invalid username. Please try again.");
                continue;
            }

            System.out.print("Enter your password: ");
            String password = scanner.next();

            currentUser = users.get(username);

            if (currentUser != null && currentUser.getPassword().equals(password)) {
                System.out.println("Authentication successful. Welcome, " +
                                                currentUser.getUsername() + "!");
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }

    private static void initializeMenu() {
        menu.put(1, new MenuItem("Burger", 5.99));
        menu.put(2, new MenuItem("Pizza", 8.99));
        menu.put(3, new MenuItem("Salad", 3.99));
        menu.put(4, new MenuItem("Pasta", 7.99));
        menu.put(5, new MenuItem("Fries", 2.99));
        menu.put(6, new MenuItem("Soda", 1.99));
        menu.put(7, new MenuItem("Ice Cream", 2.99));
        menu.put(8, new MenuItem("Coffee", 1.99));
        menu.put(9, new MenuItem("Tea", 1.99));
        menu.put(10, new MenuItem("Water", 0.00));
    }

    private static void initializeUsers() {
        User user1 = new User("user1", "pass1");
        User user2 = new User("user2", "pass2");
        User user3 = new User("user3", "pass3");
        User user4 = new User("user4", "pass4");
        User user5 = new User("user5", "pass5");

        users.put(user1.getUsername(), user1);
        users.put(user2.getUsername(), user2);
        users.put(user3.getUsername(), user3);
        users.put(user4.getUsername(), user4);
        users.put(user5.getUsername(), user5);
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        for (Map.Entry<Integer, MenuItem> entry : menu.entrySet()) {
            MenuItem item = entry.getValue();
            System.out.println(entry.getKey() + ". " +
                    item.name() + " - $" + item.price());
        }
    }

    private static Order placeOrder(Scanner userInput) {
        Order order = new Order();

        while (true) {
            System.out.println("Enter item number to add to the order " +
                    "(0 to finish and view order): ");
            int choice = userInput.nextInt();

            if (choice == 0) {
                break;
            }

            MenuItem selectedItem = menu.get(choice);

            if (selectedItem != null) {
                System.out.print("Enter quantity: ");
                int quantity = userInput.nextInt();
                OrderItem orderItem = new OrderItem(selectedItem, quantity);
                order.addItem(orderItem);
                System.out.println(orderItem.quantity()
                        + "x " + selectedItem.name() + " added to the order.");
            } else {
                System.out.println("Invalid item number. Please try again.");
            }
        }

        return order;
    }

    private static void updateUserOrderHistory(Order order) {
        currentUser.addOrderToHistory(order);
        System.out.println("Order added to your history.");
    }

    private static void displayOrderHistory() {
        System.out.println("Order History:");
        for (Order order : currentUser.getOrderHistory()) {
            order.displayOrderSummary();
        }
    }
}