import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantOS {
    static ArrayList<MenuItem> menu = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();
    static User currentUser;
    static final String MENU_FILE = "menu.txt";
    static final String USERS_FILE = "users.txt";
    static final String ORDERS_FILE = "orders.txt";

    public static void main(String[] args) {
        loadMenu();
        loadUsers();
        loadOrders();
//        initializeMenu();
        showWelcomeMenu();
    }

    public static void initializeMenu() {
        menu.add(new MenuItem("Burger", 5.99));
        menu.add(new MenuItem("Pizza", 8.99));
        menu.add(new MenuItem("Salad", 3.99));
        menu.add(new MenuItem("Fries", 2.99));
        menu.add(new MenuItem("Soda", 1.99));
        menu.add(new MenuItem("Water", 0.99));
        menu.add(new MenuItem("Coke", 1.99));
        menu.add(new MenuItem("Sprite", 1.99));
        menu.add(new MenuItem("Coffee", 1.99));
        menu.add(new MenuItem("Tea", 1.99));
        menu.add(new MenuItem("Cappuccino", 1.99));
        menu.add(new MenuItem("Latte", 1.99));
        menu.add(new MenuItem("Macchiato", 1.99));
        menu.add(new MenuItem("Espresso", 1.99));
    }

    public static void showWelcomeMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser = login();
                    if (currentUser != null) {
                        showMainMenu();
                    }
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    static User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Login successful! Welcome, " + user.username + "!");
                return user;
            }
        }

        System.out.println("Invalid username or password.");
        return null;
    }

    public static void signUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User newUser = new User(username, password);
        users.add(newUser);

        System.out.println("Registration successful!");
    }

    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Display Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View Order History");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayMenu();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    viewOrderHistory();
                    break;
                case 4:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public static void displayMenu() {
        System.out.println("Menu:");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
        }
    }

    public static void placeOrder() {
        Scanner scanner = new Scanner(System.in);
        Order order = new Order();
        int choice;

        do {
            displayMenu();
            System.out.print("Enter the item number to add to your order (0 to finish): ");
            choice = scanner.nextInt();

            if (choice >= 1 && choice <= menu.size()) {
                MenuItem selectedMenuItem = menu.get(choice - 1);

                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();

                OrderItem orderItem = new OrderItem(selectedMenuItem, quantity);
                order.addItem(orderItem);
            } else if (choice != 0) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        orders.add(order);
        System.out.println("Order placed successfully!");
    }

    public static void viewOrderHistory() {
        if (!currentUser.orders.isEmpty()) {
            System.out.println("Order History:");
            for (Order order : currentUser.orders) {
                System.out.println("Order ID: " + order.orderId);
                for (OrderItem item : order.items) {
                    System.out.println(item.quantity + "x " +
                            item.menuItem.getName() + " - $" + item.getTotal());
                }
                System.out.println("Total: $" + order.calculateTotal());
                System.out.println();
            }
        } else {
            System.out.println("No order history available.");
        }
    }

    public static void saveMenu() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MENU_FILE))) {
            oos.writeObject(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMenu() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MENU_FILE))) {
            menu = (ArrayList<MenuItem>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions or initialize a menu if the file is not found
            initializeMenu();
        }
    }

    public static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions or initialize users if the file is not found
        }
    }

    public static void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            orders = (ArrayList<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions or initialize orders if the file is not found
        }
    }
}
