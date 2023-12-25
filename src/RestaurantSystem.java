import java.io.*; // For reading and writing to files
import java.util.ArrayList; // For storing menu items, users, and orders
import java.util.Scanner;
import java.util.logging.Logger; // For logging errors and warnings to the console

public class RestaurantSystem {
    private static final Logger logger = Logger.getLogger(RestaurantSystem.class.getName());
    static ArrayList<MenuItem> menu = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();
    static User currentUser; // The user that is currently logged in
    static final String MENU_FILE = "menu.txt";
    static final String USERS_FILE = "users.txt";
    static final String ORDERS_FILE = "orders.txt";

    public static void main(String[] args) {
        loadMenu(); // Load the menu from the file
        loadUsers(); // Load the users from the file
        loadOrders(); // Load the orders from the file
        showWelcomeMenu(); // Show the welcome menu
    }

    // Initialize the menu with some default items if the menu file is not found
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

    // Show the welcome menu and handle user input
    public static void showWelcomeMenu() {
        Scanner userInput = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to the Restaurant Ordering System!\n");
            System.out.println("\t1. Login");
            System.out.println("\t2. Sign Up");
            System.out.println("\t3. Exit\n");
            System.out.print("Enter your choice: ");
            choice = userInput.nextInt();

            switch (choice) {
                case 1: // For User Login
                    currentUser = login();
                    if (currentUser != null) {
                        showMainMenu(); // Show the main menu if the login is successful
                        saveUsers(); // Save the updated users to the file
                        saveOrders(); // Save the updated orders to the file
                        saveMenu(); // Save the updated menu to the file
                        currentUser = null;
                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;
                case 2: // For User Sign Up
                    signUp();
                    break;
                case 3: // For Exiting the System
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }

    // Handle user login and return the user object if the login is successful
    static User login() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = userInput.next();
        System.out.print("Enter password: ");
        String password = userInput.next();

        for (User user : users) {
            // Check if the username and password match
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Login successful! Welcome, " + user.username + "!\n");
                return user;
            }
        }

        System.out.println("Invalid username or password.");
        return null;
    }

    // Handle user sign up and add the new user to the user's list
    public static void signUp() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = userInput.next();
        System.out.print("Enter password: ");
        String password = userInput.next();

        User newUser = new User(username, password);
        users.add(newUser); // Add the new user to the user's list

        // Save the updated users to the file
        saveUsers();

        System.out.println("Registration successful!");
    }

    // Show the main menu and handle user input for the main menu
    public static void showMainMenu() {
        Scanner userInput = new Scanner(System.in);
        int choice;

        do {
            System.out.println("What would you like to do?\n");
            System.out.println("\t1. Display Menu Items");
            System.out.println("\t2. Place Order");
            System.out.println("\t3. Cancel Order");
            System.out.println("\t4. View Order History");
            System.out.println("\t5. Add/Remove/Update Menu Items");
            System.out.println("\t6. Remove Menu Item");
            System.out.println("\t7. Update Menu Item Price");
            System.out.println("\t8. Back to Main Menu\n");
            System.out.print("Enter your choice: ");
            choice = userInput.nextInt();

            switch (choice) {
                case 1: // For Displaying Menu Items
                    displayMenu();
                    break;
                case 2: // For Placing an Order
                    placeOrder();
                    break;
                case 3: // For Canceling an Order
                    cancelOrder();
                    break;
                case 4: // For Viewing Order History of the User
                    viewOrderHistory();
                    break;
                case 5: // For Adding/Removing/Updating Menu Items
                    addMenuItem();
                    break;
                case 6: // For Removing Menu Items
                    removeMenuItem();
                    break;
                case 7: // For Updating Menu Item Prices
                    updateMenuItemPrice();
                    break;
                case 8: // For Going Back to the Welcome Menu
                    System.out.println("Exiting the system. Goodbye!");
                    showWelcomeMenu();
                    break;
                default: // For Invalid Choices
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    // Handle canceling an order by removing the order from the user's order history
    private static void cancelOrder() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter the order ID to cancel: ");
        int orderId = userInput.nextInt();

        for (Order order : orders) {
            if (order.orderId == orderId) {
                orders.remove(order); // Remove the order from the order list
                currentUser.orders.remove(order);
                System.out.println("Order cancelled successfully.");
                return;
            }
        }

        System.out.println("Order not found.");
    }

    // Display the menu items to the console
    public static void displayMenu() {
        System.out.println("\nDisplaying Menu Items:\n");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.println("\t" + (i + 1) + ". " + item.getName() + " - $" + item.getPrice());
        }
    }

    // Handle placing an order by adding the order to the user's order history
    public static void placeOrder() {
        Scanner userInput = new Scanner(System.in);
        Order order = new Order();
        int choice;

        do {
            displayMenu(); // Display the menu items to the console
            System.out.print("\nEnter the item number to add to your order (0 to finish): ");
            choice = userInput.nextInt();

            // Check if the choice is valid and add the item to the order
            if (choice >= 1 && choice <= menu.size()) {
                MenuItem selectedMenuItem = menu.get(choice - 1);

                System.out.print("Enter quantity: ");
                int quantity = userInput.nextInt();

                OrderItem orderItem = new OrderItem(selectedMenuItem, quantity);
                order.addItem(orderItem); // Add the item to the order list of the user

                System.out.println("\nItem added to OrderCart.\n");

                // Print the order item details
                System.out.println("Item: " + orderItem.menuItem.getName());
                System.out.println("Quantity: " + orderItem.quantity);
                System.out.println("Total: $" + orderItem.getTotal() + "\n");

                System.out.print("Add a note to this item (optional): ");
                orderItem.note = userInput.nextLine();

                System.out.println("Note added to item.\n");

                System.out.println("Do you want to add another item to your order? (y/n)");
                String addAnother = userInput.nextLine();

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

        orders.add(order); // Add the order to the order list
        currentUser.orders.add(order); // Add the order to the order list of the user

        // Print the order details
        System.out.println("Order ID: " + order.orderId + "\n");
        for (OrderItem item : order.items) {
            System.out.println(item.quantity + "x " +
                    item.menuItem.getName() + " - $" + item.getTotal());
        }
        System.out.println("Total: $" + order.calculateTotal());

        saveOrders(); // Save the updated orders to the file
        saveUsers(); // Save the updated users to the file
        System.out.println("Order placed successfully!\n");

        System.out.println("Do you want to place another order? (y/n)");
        String placeAnother = userInput.nextLine();

        if (placeAnother.equals("y")) {
            placeOrder(); // Place another order if the user wants to
        } else {
            showMainMenu();
        }
    }

    // Display the order history of the user
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

    // Save the menu to the file using serialization
    public static void saveMenu() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MENU_FILE))) {
            oos.writeObject(menu);
        } catch (IOException e) {
            logger.severe("Error saving menu: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Load the menu from the file using serialization
    public static void loadMenu() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MENU_FILE))) {
            menu = (ArrayList<MenuItem>) ois.readObject();
        } catch (FileNotFoundException e) {
            logger.warning("Menu file not found. Initializing menu.");
            initializeMenu();
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Error loading menu: " + e.getMessage());
            initializeMenu();
        }
    }


    // Save the users to the file using serialization
    public static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            logger.severe("Error saving users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Load the users from the file using serialization
    public static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.warning("Error loading users: " + e.getMessage());
            // Handle exceptions or initialize users if the file is not found
        }
    }

    // Save the orders to the file using serialization
    public static void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            logger.severe("Error saving orders: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Load the orders from the file using serialization
    public static void loadOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            orders = (ArrayList<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.warning("Error loading orders: " + e.getMessage());
            // Handle exceptions or initialize orders if the file is not found
        }
    }

    // Add a new menu item to the menu
    public static void addMenuItem() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter the name of the new menu item: ");
        String name = userInput.nextLine();
        System.out.print("Enter the price of the new menu item: ");
        double price = userInput.nextDouble(); // Use nextDouble() for double input

        MenuItem newItem = new MenuItem(name, price);
        menu.add(newItem); // Add the new menu item to the menu list

        // Save the updated menu to the file
        saveMenu();

        System.out.println("Menu item added successfully.");
    }

    // Remove a menu item from the menu by its index
    public static void removeMenuItem() {
        displayMenu(); // Display the menu items to the console
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter the number of the item to remove: ");
        int choice = userInput.nextInt();

        if (choice >= 1 && choice <= menu.size()) {
            menu.remove(choice - 1); // Remove the menu item from the menu list

            // Save the updated menu to the file
            saveMenu();

            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    // Update the price of a menu item by its index in the menu
    public static void updateMenuItemPrice() {
        displayMenu(); // Display the menu items to the console
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter the number of the item to update: ");
        int choice = userInput.nextInt();

        // Check if the choice is valid and update the price of the menu item
        if (choice >= 1 && choice <= menu.size()) {
            MenuItem itemToUpdate = menu.get(choice - 1);
            System.out.print("Enter the new price: ");
            double newPrice = userInput.nextDouble();
            itemToUpdate.setPrice(newPrice); // Update the price of the menu item

            // Save the updated menu to the file
            saveMenu();

            System.out.println("Menu item price updated successfully.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}
