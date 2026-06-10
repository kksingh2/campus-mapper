import java.util.ArrayList;
import java.util.Scanner;

// Main class containing the entry point and menu-driven logic of the application
public class Main {
    // Static member variable declarations for database management and input scanning
    private static DatabaseManager dbManager; // Manager for database operations
    private static Scanner scanner = new Scanner(System.in); // Scanner for reading user input

    public static void main(String[] args) {
        // Database connection URL string
        String databaseUrl = "jdbc:sqlite:data/UniversityMapper.db";
        dbManager = new DatabaseManager(databaseUrl); // Initialize the database manager with the connection URL
        boolean running = true; // Control variable for the main loop

        while (running) {
            System.out.println("\nUniversity Campus System");
            System.out.println("1. Manage Colleges");
            System.out.println("2. Manage Routes");
            System.out.println("3. Manage Users");
            System.out.println("4. Manage Accessibility Levels");
            System.out.println("5. Display Data");
            System.out.println("6. Find Route");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                manageColleges(); // Call the function to handle college management
            } else if (choice == 2) {
                manageRoutes(); // Call the function to handle route management
            } else if (choice == 3) {
                manageUsers(); // Call the function to handle user management
            } else if (choice == 4) {
                manageAccessibilityLevels(); // Call the function to handle accessibility level management
            } else if (choice == 5) {
                loadAndDisplayData(); // Call the function to display all data
            } else if(choice == 6) {
                findOptimalRoute(); // Call the function to find the optimal route
            } else if (choice == 7) {
                running = false; // Exit the application loop
            } else {
                System.out.println("Invalid choice. Please try again."); // Handle invalid menu choice
            }
        }
    }

    // Method to handle the route-finding functionality
    private static void findOptimalRoute() {
        RouteFinder routeFinder = new RouteFinder(); // Instantiate a new RouteFinder object for finding routes
        // Display the user prompt for finding a route
        System.out.println("\nFinding Optimal Route:");
        displayAllUsers(); // Display all users to choose from

        // Ask for the user's ID and read the input
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt(); // Store the user ID from input

        // Retrieve the user data from the database
        CustomHashMap usersMap = dbManager.loadUsers(); // Load users into a custom hashmap
        User user = (User) usersMap.get(userId); // Get the user object from the hashmap using the provided ID
        if (user == null) { // If the user is not found
            System.out.println("User not found."); // Display a not found message
            return; // Exit the method early
        }

        int userAccessibilityLevel = user.getAccessibilityLevel(); // Retrieve the user's accessibility level

        // Display the college selection prompt
        displayAllColleges(); // Display all colleges to choose from

        // Read the start and end college IDs from the user input
        System.out.print("Enter Start College ID: ");
        int startCollegeId = scanner.nextInt(); // Store the start college ID from input
        System.out.print("Enter End College ID: ");
        int endCollegeId = scanner.nextInt(); // Store the end college ID from input

        // Perform the route finding operation and display the result
        CustomHashMap collegesMap = dbManager.loadColleges(); // Load colleges into a custom hashmap
        ArrayList<Integer> optimalRoute = routeFinder.findOptimalRoute(startCollegeId, endCollegeId, userAccessibilityLevel, collegesMap);
        System.out.println("Optimal Route: " + optimalRoute); // Display the optimal route as a list of college IDs
    }
    private static void manageColleges() {
        boolean back = false;
        while (!back) {
            System.out.println("\nCollege Management:");
            System.out.println("1. Create College");
            System.out.println("2. Read College");
            System.out.println("3. Update College");
            System.out.println("4. Delete College");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int collegeChoice = scanner.nextInt();

            if (collegeChoice == 1) {
                createCollege();
            } else if (collegeChoice == 2) {
                readCollege();
            } else if (collegeChoice == 3) {
                updateCollege();
            } else if (collegeChoice == 4) {
                deleteCollege();
            } else if (collegeChoice == 5) {
                back = true;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageRoutes() {
        boolean back = false;
        while (!back) {
            System.out.println("\nRoute Management:");
            System.out.println("1. Create Route");
            System.out.println("2. Read Route");
            System.out.println("3. Update Route");
            System.out.println("4. Delete Route");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int routeChoice = scanner.nextInt();

            if (routeChoice == 1) {
                createRoute();
            } else if (routeChoice == 2) {
                readRoute();
            } else if (routeChoice == 3) {
                updateRoute();
            } else if (routeChoice == 4) {
                deleteRoute();
            } else if (routeChoice == 5) {
                back = true;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageUsers() {
        boolean back = false;
        while (!back) {
            System.out.println("\nUser Management:");
            System.out.println("1. Create User");
            System.out.println("2. Read User");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int userChoice = scanner.nextInt();

            if (userChoice == 1) {
                createUser();
            } else if (userChoice == 2) {
                readUser();
            } else if (userChoice == 3) {
                updateUser();
            } else if (userChoice == 4) {
                deleteUser();
            } else if (userChoice == 5) {
                back = true;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void manageAccessibilityLevels() {
        boolean back = false;
        while (!back) {
            System.out.println("\nAccessibility Level Management:");
            System.out.println("1. Create Accessibility Level");
            System.out.println("2. Read Accessibility Level");
            System.out.println("3. Update Accessibility Level");
            System.out.println("4. Delete Accessibility Level");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                createAccessibilityLevel();
            } else if (choice == 2) {
                readAccessibilityLevel();
            } else if (choice == 3) {
                updateAccessibilityLevel();
            } else if (choice == 4) {
                deleteAccessibilityLevel();
            } else if (choice == 5) {
                back = true;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // This method is responsible for printing out all accessibility levels to the console.
    private static void displayAllAccessibilityLevels() {
        System.out.println("\nAvailable Accessibility Levels:");

        // Call the method 'loadAccessibilityLevels' from 'dbManager' which is expected to return a
        // CustomHashMap containing all the accessibility levels from the database.
        CustomHashMap levelsMap = dbManager.loadAccessibilityLevels();

        // Iterate over the set of entries in the 'levelsMap'. An 'Entry' is a key-value pair in the map.
        // The colon (:) is used in the for-each loop to iterate over each element in the iterable collection.
        // On the left side of the colon, we declare a variable to hold the current element during iteration ('entry'),
        // and on the right side, we specify the collection to iterate over ('levelsMap.entrySet()').
        for (CustomHashMap.Entry entry : levelsMap.entrySet()) {
            // For each entry in the set, print out the key and value.
            // 'entry.getKey()' retrieves the key part of the pair, which in this context is the ID of the accessibility level.
            // 'entry.getValue()' retrieves the value part of the pair, which is the description of the accessibility level.
            System.out.println("Level: " + entry.getKey() + ", Description: " + entry.getValue());
        }
    }


    private static void createAccessibilityLevel() {
        displayAllAccessibilityLevels();
        System.out.print("Enter Accessibility Level: ");
        int level = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        AccessibilityLevel accessibilityLevel = new AccessibilityLevel(level, description);
        dbManager.createAccessibilityLevel(accessibilityLevel);
        System.out.println("Accessibility Level created successfully.");
    }

    private static void readAccessibilityLevel() {
        displayAllAccessibilityLevels();
        System.out.print("Enter Accessibility Level to Read: ");
        int level = scanner.nextInt();
        AccessibilityLevel accessibilityLevel = dbManager.readAccessibilityLevel(level);

        if (accessibilityLevel != null) {
            System.out.println(accessibilityLevel);
        } else {
            System.out.println("Accessibility Level not found.");
        }
    }

    private static void updateAccessibilityLevel() {
        displayAllAccessibilityLevels();
        System.out.print("Enter Accessibility Level to Update: ");
        int level = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter New Description: ");
        String description = scanner.nextLine();

        AccessibilityLevel accessibilityLevel = new AccessibilityLevel(level, description);
        dbManager.updateAccessibilityLevel(accessibilityLevel);
        System.out.println("Accessibility Level updated successfully.");
    }

    private static void deleteAccessibilityLevel() {
        displayAllAccessibilityLevels();
        System.out.print("Enter Accessibility Level to Delete: ");
        int level = scanner.nextInt();
        dbManager.deleteAccessibilityLevel(level);
        System.out.println("Accessibility Level deleted successfully.");
    }

    private static void displayAllUsers() {
        System.out.println("\nAvailable Users:");
        CustomHashMap usersMap = dbManager.loadUsers();

        for (CustomHashMap.Entry entry : usersMap.entrySet()) {
            User user = (User) entry.getValue();
            System.out.println("ID: " + entry.getKey() + ", Name: " + user.getName());
        }
    }

    private static void createUser() {
        System.out.println("\nCreating a New User:");
        displayAllUsers();

        System.out.print("Enter User ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Check if the user ID already exists
        CustomHashMap usersMap = dbManager.loadUsers();
        if (usersMap.contains(id)) {
            System.out.println("User ID already exists. Please try a different ID.");
            return; // Return to the menu if user ID exists
        }

        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Accessibility Level: ");
        int level = scanner.nextInt();

        User newUser = new User(id, name, level);
        dbManager.createUser(newUser);
        System.out.println("User created successfully.");
    }

    private static void readUser() {
        displayAllUsers();
        System.out.print("Enter User ID to Read: ");
        int id = scanner.nextInt();
        User user = dbManager.readUser(id);

        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

    private static void updateUser() {
        displayAllUsers();
        System.out.print("Enter User ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline

        System.out.print("Enter New User Name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter New Accessibility Level: ");
        int newLevel = scanner.nextInt();

        // Create an updated User object
        User updatedUser = new User(id, newName, newLevel);

        // Call the update method in dbManager
        dbManager.updateUser(updatedUser);

        System.out.println("User updated successfully.");
    }

    private static void deleteUser() {
        displayAllUsers();
        System.out.print("Enter User ID to Delete: ");
        int id = scanner.nextInt();
        dbManager.deleteUser(id);
        System.out.println("User deleted successfully.");
    }

    private static void displayAllColleges() {
        System.out.println("\nAvailable Colleges:");
        CustomHashMap collegeMap = dbManager.loadColleges();

        // Iterate through the elements of the SimpleHashMap
        for (CustomHashMap.Element element : collegeMap.getElements()) {
            if (element != null) {
                College college = (College) element.getValue();
                System.out.println("ID: " + element.getKey() + ", Name: " + college.getCollegeName());
            }
        }
    }

    private static void createCollege() {
        System.out.print("Enter College ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter College Name: ");
        String name = scanner.nextLine();

        College newCollege = new College(id, name);
        dbManager.createCollege(newCollege);
        System.out.println("College created successfully.");
    }

    private static void readCollege() {
        System.out.print("Enter College ID to Read: ");
        int id = scanner.nextInt();
        College college = dbManager.readCollege(id);

        if (college != null) {
            System.out.println(college);
        } else {
            System.out.println("College not found.");
        }
    }

    private static void updateCollege() {
        displayAllColleges();
        System.out.print("Enter College ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter New College Name: ");
        String name = scanner.nextLine();

        College updatedCollege = new College(id, name);
        dbManager.updateCollege(updatedCollege);
        System.out.println("College updated successfully.");
    }

    private static void deleteCollege() {
        System.out.print("Enter College ID to Delete: ");
        int id = scanner.nextInt();
        dbManager.deleteCollege(id);
        System.out.println("College deleted successfully.");
    }

    private static void displayAllRoutes() {
        System.out.println("\nAvailable Routes:");
        CustomHashMap routeMap = dbManager.loadRouteIDMap(); // Raw type used here
        for (Object entryObject : routeMap.entrySet()) {
            CustomHashMap.Entry entry = (CustomHashMap.Entry) entryObject;
            Route route = (Route) entry.getValue();
            System.out.println("Route ID: " + route.getRouteID() + ", Start ID: " + route.getStartCollegeID() +
                    ", End ID: " + route.getEndCollegeID() + ", Time Taken: " + route.getTimeTaken() +
                    ", Accessibility Level: " + route.getAccessibilityLevel());
        }
    }

    private static void createRoute() {
        displayAllRoutes();
        System.out.print("Enter Route ID: ");
        int routeId = scanner.nextInt();

        System.out.print("Enter Start College ID: ");
        int startId = scanner.nextInt();

        System.out.print("Enter End College ID: ");
        int endId = scanner.nextInt();

        System.out.print("Enter Time Taken (in minutes): ");
        int timeTaken = scanner.nextInt();

        System.out.print("Enter Accessibility Level: ");
        int accessibilityLevel = scanner.nextInt();

        Route newRoute = new Route(routeId, startId, endId, timeTaken, accessibilityLevel);
        dbManager.createRoute(newRoute);
        System.out.println("Route created successfully.");
    }

    private static void readRoute() {
        displayAllRoutes();
        System.out.print("Enter Route ID to Read: ");
        int routeId = scanner.nextInt();
        Route route = dbManager.readRoute(routeId);

        if (route != null) {
            System.out.println(route);
        } else {
            System.out.println("Route not found.");
        }
    }

    private static void updateRoute() {
        displayAllRoutes();
        System.out.print("Enter Route ID to Update: ");
        int routeId = scanner.nextInt();

        System.out.print("Enter New Start College ID: ");
        int startId = scanner.nextInt();

        System.out.print("Enter New End College ID: ");
        int endId = scanner.nextInt();

        System.out.print("Enter New Time Taken (in minutes): ");
        int timeTaken = scanner.nextInt();

        System.out.print("Enter New Accessibility Level: ");
        int accessibilityLevel = scanner.nextInt();

        Route updatedRoute = new Route(routeId, startId, endId, timeTaken, accessibilityLevel);
        dbManager.updateRoute(updatedRoute);
        System.out.println("Route updated successfully.");
    }

    private static void deleteRoute() {
        displayAllRoutes();
        System.out.print("Enter Route ID to Delete: ");
        int routeId = scanner.nextInt();
        dbManager.deleteRoute(routeId);
        System.out.println("Route deleted successfully.");
    }

    private static void loadAndDisplayData() {
        loadAndDisplayRoutes(dbManager); // Load and display route information
        loadAndDisplayUsers(dbManager); // Load and display user information
        loadAndDisplayColleges(dbManager); // Load and display college information
        loadAndDisplayAccessibilityLevels(dbManager); // Load and display accessibility level information
    }

    private static void loadAndDisplayRoutes(DatabaseManager dbManager) {
        try {
            CustomHashMap routeMap = dbManager.loadRouteIDMap();
            System.out.println("\nLoaded Route Information:");

            for (CustomHashMap.Element element : routeMap.getElements()) {
                if (element != null) {
                    System.out.println(element.getValue());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading routes: " + e.getMessage());
        }
    }

    private static void loadAndDisplayUsers(DatabaseManager dbManager) {
        try {
            CustomHashMap userMap = dbManager.loadUsers();
            System.out.println("\nLoaded User Information:");

            for (CustomHashMap.Element element : userMap.getElements()) {
                if (element != null) {
                    System.out.println(element.getValue());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    private static void loadAndDisplayColleges(DatabaseManager dbManager) {
        try {
            CustomHashMap collegeMap = dbManager.loadColleges();
            dbManager.inferAndAssignNeighbours(collegeMap);

            System.out.println("\nLoaded College Information with Neighbours:");
            for (CustomHashMap.Element element : collegeMap.getElements()) {
                if (element != null) {
                    System.out.println(element.getValue());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading colleges: " + e.getMessage());
        }
    }

    private static void loadAndDisplayAccessibilityLevels(DatabaseManager dbManager) {
        try {
            CustomHashMap levelsMap = dbManager.loadAccessibilityLevels();
            System.out.println("\nLoaded Accessibility Level Information:");

            for (CustomHashMap.Element element : levelsMap.getElements()) {
                if (element != null) {
                    System.out.println("Level: " + element.getKey() + ", Description: " + element.getValue());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading accessibility levels: " + e.getMessage());
        }
    }
}