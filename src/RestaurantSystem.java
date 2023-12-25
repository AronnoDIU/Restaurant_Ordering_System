import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantSystem {
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
            System.out.println("Welcome to the Restaurant Ordering System!");
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
                    } else {
                        System.out.println("Login failed. Please try again.");
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
            System.out.println("Welcome to the Restaurant Ordering System! What would you like to do?");
            System.out.println("1. Display Menu Items");
            System.out.println("2. Place Order");
            System.out.println("3. View Order History");
            System.out.println("4. Add/Remove/Update Menu Items");
            System.out.println("5. Remove Menu Item");
            System.out.println("6. Update Menu Item Price");
            System.out.println("7. Back to Main Menu");
            System.out.println("8. Exit");
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
                    addMenuItem();
                    break;
                case 5:
                    removeMenuItem();
                    break;
                case 6:
                    updateMenuItemPrice();
                    break;
                case 7:
                    showWelcomeMenu();
                    break;
                case 8:
                    System.out.println("Exiting the system. Goodbye!");
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

                System.out.println("Item added to order.");

                // Print the order item details
                System.out.println("Item: " + orderItem.menuItem.getName());
                System.out.println("Quantity: " + orderItem.quantity);
                System.out.println("Total: $" + orderItem.getTotal());

                System.out.print("Add a note to this item (optional): ");
                orderItem.note = scanner.nextLine();

                System.out.println("Note added to item.");

                System.out.println("Do you want to add another item to your order? (y/n)");
                String addAnother = scanner.nextLine();

                if (addAnother.equals("y")) {
                    choice = -1;
                } else {
                    choice = 0;
                }
            } else if (choice == 0) {
                if (order.items.isEmpty()) {
                    System.out.println("You must add at least one item to your order.");
                    choice = -1;
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        orders.add(order);
        currentUser.orders.add(order);

        // Print the order details
        System.out.println("Order ID: " + order.orderId);
        for (OrderItem item : order.items) {
            System.out.println(item.quantity + "x " +
                    item.menuItem.getName() + " - $" + item.getTotal());
        }
        System.out.println("Total: $" + order.calculateTotal());

        saveOrders();
        saveUsers();
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

    @SuppressWarnings("unchecked")
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

    public static void addMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the new menu item: ");
        String name = scanner.nextLine();
        System.out.print("Enter the price of the new menu item: ");
        double price = scanner.nextDouble();

        MenuItem newItem = new MenuItem(name, price);
        menu.add(newItem);

        // Save the updated menu to the file
        saveMenu();

        System.out.println("Menu item added successfully.");
    }

    public static void removeMenuItem() {
        displayMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item to remove: ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= menu.size()) {
            menu.remove(choice - 1);

            // Save the updated menu to the file
            saveMenu();

            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void updateMenuItemPrice() {
        displayMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item to update: ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= menu.size()) {
            MenuItem itemToUpdate = menu.get(choice - 1);
            System.out.print("Enter the new price: ");
            double newPrice = scanner.nextDouble();
            itemToUpdate.setPrice(newPrice);

            // Save the updated menu to the file
            saveMenu();

            System.out.println("Menu item price updated successfully.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}
