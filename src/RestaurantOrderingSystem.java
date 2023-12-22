import java.io.*;
import java.util.*;

public class RestaurantOrderingSystem {
    private static final Map<Integer, MenuItem> menu = new HashMap<>();
    private static final Order currentOrder = new Order();
    private static User currentUser;

    public static void main(String[] args) {
        loadMenu(); // Load menu from file
        displayMenu();

        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Continue as Guest");
            System.out.println("4. Exit");

            int option = userInput.nextInt();

            switch (option) {
                case 1:
                    loginUser(userInput);
                    break;
                case 2:
                    signUpUser(userInput);
                    break;
                case 3:
                    placeOrder(userInput);
                    break;
                case 4:
                    saveOrderToFile();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            placeOrder(userInput);
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter password: ");
        String password = scanner.next();

        currentUser = UserManager.loginUser(username, password);
    }

    private static void signUpUser(Scanner scanner) {
        System.out.println("Enter a new username: ");
        String username = scanner.next();
        System.out.println("Enter a password: ");
        String password = scanner.next();

        UserManager.addUser(username, password);
    }

    private static void placeOrder(Scanner scanner) {
        while (true) {
            System.out.println("Enter item number to add to the order (0 to finish and view order): ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                break;
            }

            MenuItem selectedItem = menu.get(choice);
            if (selectedItem != null) {
                currentOrder.addItem(selectedItem);
                System.out.println(selectedItem.getName() + " added to the order.");
            } else {
                System.out.println("Invalid item number. Please try again.");
            }
        }

        if (currentUser != null) {
            currentUser.addOrderToHistory(currentOrder);
        }

        currentOrder.displayOrderSummary();
    }

    private static void loadMenu() {
        // Load menu from a file (e.g., menu.txt)
        try (BufferedReader reader = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int itemNumber = Integer.parseInt(parts[0]);
                String itemName = parts[1];
                double itemPrice = Double.parseDouble(parts[2]);
                menu.put(itemNumber, new MenuItem(itemName, itemPrice));
            }
        } catch (IOException e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }

    private static void saveOrderToFile() {
        // Save the current order to a file (e.g., orders.txt)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
            writer.write("Order Summary for " + (currentUser != null ? currentUser.getUsername() : "Guest") + ":\n");
            for (MenuItem item : currentOrder.getItems()) {
                writer.write(item.getName() + " - $" + item.getPrice() + "\n");
            }
            writer.write("Total: $" + currentOrder.calculateTotal() + "\n");
            writer.write("----------------------------\n");
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
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

//    private static void initializeUsers() {
//        User user1 = new User("user1", "pass1");
//        User user2 = new User("user2", "pass2");
//        User user3 = new User("user3", "pass3");
//        User user4 = new User("user4", "pass4");
//        User user5 = new User("user5", "pass5");
//
//        users.put(user1.getUsername(), user1);
//        users.put(user2.getUsername(), user2);
//        users.put(user3.getUsername(), user3);
//        users.put(user4.getUsername(), user4);
//        users.put(user5.getUsername(), user5);
//    }

    private static void displayMenu() {
        System.out.println("Menu:");
        for (Map.Entry<Integer, MenuItem> entry : menu.entrySet()) {
            MenuItem item = entry.getValue();
            System.out.println(entry.getKey() + ". " +
                    item.name() + " - $" + item.price());
        }
    }

//    private static Order placeOrder(Scanner userInput) {
//        Order order = new Order();
//
//        while (true) {
//            System.out.println("Enter item number to add to the order " +
//                    "(0 to finish and view order): ");
//            int choice = userInput.nextInt();
//
//            if (choice == 0) {
//                break;
//            }
//
//            MenuItem selectedItem = menu.get(choice);
//
//            if (selectedItem != null) {
//                System.out.print("Enter quantity: ");
//                int quantity = userInput.nextInt();
//                OrderItem orderItem = new OrderItem(selectedItem, quantity);
//                order.addItem(orderItem);
//                System.out.println(orderItem.quantity()
//                        + "x " + selectedItem.name() + " added to the order.");
//            } else {
//                System.out.println("Invalid item number. Please try again.");
//            }
//        }
//
//        return order;
//    }

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