import java.util.*;

public class RestaurantOrderingSystem {
    private static final Map<Integer, MenuItem> menu = new HashMap<>();
    private static final List<User> users = new ArrayList<>();
    private static final Order currentOrder = new Order();

    public static void main(String[] args) {
        initializeMenu();
        initializeUsers();

        Scanner userInput = new Scanner(System.in);

        System.out.println("Welcome to the Restaurant Ordering System!");

        // User Authentication Loop
        User currentUser; // = authenticateUser(username, password);
        while (true) {
            System.out.println("Enter your username (or type 'guest' for guest access): ");
            String username = userInput.nextLine();

            if (username.equalsIgnoreCase("guest")) {
                currentUser = new User("guest", "");
                System.out.println("Welcome, guest!");
                break;
            }

            System.out.println("Enter your password: ");
            String password = userInput.nextLine();

            currentUser = authenticateUser(username, password);

            if (currentUser != null) {
                System.out.println("Welcome, " + currentUser.getUsername() + "!");
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }

        displayMenu();

        while (true) {
            System.out.println("Enter item number to add to the order " +
                    "(0 to finish and view order): ");
            int choice = userInput.nextInt();

            if (choice == 0) {
                break;
            }

            MenuItem selectedItem = menu.get(choice);
            if (selectedItem != null) {
                System.out.println("Enter quantity: ");
                int quantity = userInput.nextInt();

                currentOrder.addItem(selectedItem, quantity);
                System.out.println(quantity + " " +
                        selectedItem.name() + "(s) added to the order.");
            } else {
                System.out.println("Invalid item number. Please try again.");
            }
        }

        currentUser.addOrderToHistory(currentOrder);
        currentOrder.displayOrderSummary();
    }

    private static void initializeMenu() {
        System.out.println("Initializing menu...");
        menu.put(1, new MenuItem("Burger", 5.99));
        menu.put(2, new MenuItem("Pizza", 8.99));
        menu.put(3, new MenuItem("Salad", 3.99));
        menu.put(4, new MenuItem("Pasta", 7.99));
        menu.put(5, new MenuItem("Fries", 2.99));
        menu.put(6, new MenuItem("Soda", 1.99));
        menu.put(7, new MenuItem("Ice Cream", 2.99));
        menu.put(8, new MenuItem("Coffee", 1.99));
        menu.put(9, new MenuItem("Tea", 1.99));
        menu.put(10, new MenuItem("Milkshake", 3.99));
    }

    private static void initializeUsers() {
        users.add(new User("user1", "pass1"));
        users.add(new User("user2", "pass2"));
        users.add(new User("user3", "pass3"));
        users.add(new User("user4", "pass4"));
        users.add(new User("user5", "pass5"));
    }

    private static User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        for (Map.Entry<Integer, MenuItem> entry : menu.entrySet()) {
            MenuItem item = entry.getValue();
            System.out.println(entry.getKey() + ". " +
                    item.name() + " - $" + item.price());
        }
    }
}