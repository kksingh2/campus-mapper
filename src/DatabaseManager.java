import java.sql.*;

// Define the DatabaseManager class to handle database operations
public class DatabaseManager {
    private String url; // URL of the SQLite database

    // Constructor of DatabaseManager that initializes it with the database URL
    public DatabaseManager(String dbUrl) {
        this.url = dbUrl;
    }

    // Private method to establish a connection to the database
    private Connection connect() {
        Connection conn = null; // Initialize the Connection object to null
        try {
            // Attempt to create a connection to the database using the provided URL
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            // Catch any SQL exceptions and print an error message
            System.out.println("Connection failed: " + e.getMessage());
        }
        // Return the Connection object, which may be null if the connection failed
        return conn;
    }

    // Public method to load a map of routes from the database
    public CustomHashMap loadRouteIDMap() {
        // SQL query to select all columns from the Connections table
        String sql = "SELECT RouteID, StartCollegeID, EndCollegeID, TimeTaken, AccessibilityLevel FROM Connections";
        CustomHashMap routeIDMap = new CustomHashMap(); // Instantiate a new CustomHashMap

        // Try-with-resources to automatically close resources after use
        try (Connection conn = this.connect(); // Establish a connection
             Statement stmt = conn.createStatement(); // Create a Statement object
             ResultSet rs = stmt.executeQuery(sql)) { // Execute the query and obtain the ResultSet

            // Iterate over each row in the ResultSet
            while (rs.next()) {
                // Create a new Route object using the data from the current row
                Route route = new Route(
                        rs.getInt("RouteID"), rs.getInt("StartCollegeID"), rs.getInt("EndCollegeID"),
                        rs.getInt("TimeTaken"), rs.getInt("AccessibilityLevel")
                );
                // Add the route to the map with the route ID as the key
                routeIDMap.add(route.getRouteID(), route);
            }
        } catch (SQLException e) {
            // Catch any SQL exceptions and print an error message
            System.out.println("Error loading routes: " + e.getMessage());
        }
        // Return the map containing all routes
        return routeIDMap;
    }


    // Method to load all user data from the database and store it in a CustomHashMap
    public CustomHashMap loadUsers() {
        // SQL query string to select all fields from the Users table
        String sql = "SELECT * FROM Users";
        // Instantiate a new CustomHashMap to store the User objects
        CustomHashMap usersMap = new CustomHashMap();

        // Try-with-resources block to ensure proper resource management
        try (Connection conn = this.connect(); // Call the connect method to establish a database connection
             Statement stmt = conn.createStatement(); // Create a Statement object to execute the SQL query
             ResultSet rs = stmt.executeQuery(sql)) { // Execute the query and get the result set

            // Loop through the ResultSet while there are more records
            while (rs.next()) {
                // Instantiate a new User object using data from the current row in the ResultSet
                User user = new User(
                        rs.getInt("UserID"), // Get the UserID from the current row
                        rs.getString("Name"), // Get the Name from the current row
                        rs.getInt("AccessibilityLevel") // Get the AccessibilityLevel from the current row
                );
                // Add the new User object to the CustomHashMap with the UserID as the key
                usersMap.add(user.getUserID(), user);
            }
        } catch (SQLException e) {
            // Catch any SQLExceptions and print an error message to the console
            System.out.println("Error loading users: " + e.getMessage());
        }
        // Return the CustomHashMap containing all loaded User objects
        return usersMap;
    }

    // Method to load all accessibility level data from the database and store it in a CustomHashMap
    public CustomHashMap loadAccessibilityLevels() {
        // SQL query string to select all fields from the AccessibilityLevels table
        String sql = "SELECT * FROM AccessibilityLevels";
        // Instantiate a new CustomHashMap to store the descriptions of accessibility levels
        CustomHashMap levelsMap = new CustomHashMap();

        // Try-with-resources block to ensure proper resource management
        try (Connection conn = this.connect(); // Call the connect method to establish a database connection
             Statement stmt = conn.createStatement(); // Create a Statement object to execute the SQL query
             ResultSet rs = stmt.executeQuery(sql)) { // Execute the query and get the result set

            // Loop through the ResultSet while there are more records
            while (rs.next()) {
                // Add each accessibility level and its description to the CustomHashMap
                levelsMap.add(rs.getInt("AccessibilityLevel"), // Get the AccessibilityLevel (key) from the current row
                        rs.getString("Description")); // Get the Description (value) from the current row
            }
        } catch (SQLException e) {
            // Catch any SQLExceptions and print an error message to the console
            System.out.println("Error loading accessibility levels: " + e.getMessage());
        }
        // Return the CustomHashMap containing all loaded accessibility levels and their descriptions
        return levelsMap;
    }
    public CustomHashMap loadColleges() {
        String sql = "SELECT * FROM Colleges";
        CustomHashMap collegesMap = new CustomHashMap();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                College college = new College(rs.getInt("CollegeID"), rs.getString("CollegeName"));
                collegesMap.add(rs.getInt("CollegeID"), college);
                loadRoutesForCollege(college);
            }

        } catch (SQLException e) {
            System.out.println("Error loading colleges: " + e.getMessage());
        }

        return collegesMap;
    }

    private void loadRoutesForCollege(College college) {
        String sql = "SELECT * FROM Connections WHERE StartCollegeID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, college.getCollegeID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Route route = new Route
                        (
                                rs.getInt("RouteID"), rs.getInt("StartCollegeID"),
                                rs.getInt("EndCollegeID"), rs.getInt("TimeTaken"),
                                rs.getInt("AccessibilityLevel")
                        );
                college.addRoute(route);
            }
        } catch (SQLException e) {
            System.out.println("Error loading routes for college: " + e.getMessage());
        }
    }

    // This method is responsible for determining which colleges are neighbors to each other based on the connections in the database.
    public void inferAndAssignNeighbours(CustomHashMap collegesMap) {
        // SQL statement selects only the StartCollegeID and EndCollegeID columns from the Connections table.
        String sql = "SELECT StartCollegeID, EndCollegeID FROM Connections";

        // Attempt to establish a database connection and create a statement to be executed.
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through each row returned by the query.
            while (rs.next()) {
                // Retrieve the starting college ID and ending college ID from the current row.
                int startID = rs.getInt("StartCollegeID");
                int endID = rs.getInt("EndCollegeID");

                // Fetch the College objects corresponding to the start and end IDs from the provided collegesMap.
                College startCollege = (College) collegesMap.get(startID);
                College endCollege = (College) collegesMap.get(endID);

                // Ensure that both College objects are found in the map (i.e., they are not null).
                if (startCollege != null && endCollege != null) {
                    // If both colleges exist, add each other as neighbors.
                    // For the startCollege, add the name of the endCollege to its list of neighbors.
                    startCollege.addNeighbour(endCollege.getCollegeName());
                    // For the endCollege, add the name of the startCollege to its list of neighbors.
                    endCollege.addNeighbour(startCollege.getCollegeName());
                }
            }
        } catch (SQLException e) {
            // If there's an SQL exception (such as a connection failure or bad query), print an error message.
            System.out.println("Error assigning neighbours: " + e.getMessage());
        }
    }

    // CREATE operations
    public void createAccessibilityLevel(AccessibilityLevel accessibilityLevel) {
        String sql = "INSERT INTO AccessibilityLevels (AccessibilityLevel, Description) VALUES (?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accessibilityLevel.getLevel());
            pstmt.setString(2, accessibilityLevel.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating accessibility level: " + e.getMessage());
        }
    }

    public void createRoute(Route route) {
        String sql = "INSERT INTO Connections (RouteID, StartCollegeID, EndCollegeID, TimeTaken, AccessibilityLevel) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, route.getRouteID());
            pstmt.setInt(2, route.getStartCollegeID());
            pstmt.setInt(3, route.getEndCollegeID());
            pstmt.setInt(4, route.getTimeTaken());
            pstmt.setInt(5, route.getAccessibilityLevel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating route: " + e.getMessage());
        }
    }

    public void createUser(User user) {
        String sql = "INSERT INTO Users (UserID, Name, AccessibilityLevel) VALUES (?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getName());
            pstmt.setInt(3, user.getAccessibilityLevel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public void createCollege(College college) {
        String sql = "INSERT INTO Colleges (CollegeID, CollegeName) VALUES (?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, college.getCollegeID());
            pstmt.setString(2, college.getCollegeName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating college: " + e.getMessage());
        }
    }

    // READ operations

    public AccessibilityLevel readAccessibilityLevel(int level) {
        String sql = "SELECT * FROM AccessibilityLevels WHERE AccessibilityLevel = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, level);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String description = rs.getString("Description");
                return new AccessibilityLevel(level, description);
            }
        } catch (SQLException e) {
            System.out.println("Error reading accessibility level: " + e.getMessage());
        }
        return null;
    }

    public Route readRoute(int routeID) {
        String sql = "SELECT * FROM Connections WHERE RouteID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, routeID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int startCollegeID = rs.getInt("StartCollegeID");
                int endCollegeID = rs.getInt("EndCollegeID");
                int timeTaken = rs.getInt("TimeTaken");
                int accessibilityLevel = rs.getInt("AccessibilityLevel");
                return new Route(routeID, startCollegeID, endCollegeID, timeTaken, accessibilityLevel);
            }
        } catch (SQLException e) {
            System.out.println("Error reading route: " + e.getMessage());
        }
        return null;
    }

    public User readUser(int userID) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                int accessibilityLevel = rs.getInt("AccessibilityLevel");
                return new User(userID, name, accessibilityLevel);
            }
        } catch (SQLException e) {
            System.out.println("Error reading user: " + e.getMessage());
        }
        return null;
    }

    public College readCollege(int collegeID) {
        String sql = "SELECT * FROM Colleges WHERE CollegeID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, collegeID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String collegeName = rs.getString("CollegeName");
                College college = new College(collegeID, collegeName);
                // You may also want to fetch and set neighbours here
                return college;
            }
        } catch (SQLException e) {
            System.out.println("Error reading college: " + e.getMessage());
        }
        return null;
    }

    // UPDATE operations

    public void updateAccessibilityLevel(AccessibilityLevel accessibilityLevel) {
        String sql = "UPDATE AccessibilityLevels SET Description = ? WHERE AccessibilityLevel = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accessibilityLevel.getDescription());
            pstmt.setInt(2, accessibilityLevel.getLevel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating accessibility level: " + e.getMessage());
        }
    }

    public void updateRoute(Route route) {
        String sql = "UPDATE Connections SET StartCollegeID = ?, EndCollegeID = ?, TimeTaken = ?, AccessibilityLevel = ? WHERE RouteID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, route.getStartCollegeID());
            pstmt.setInt(2, route.getEndCollegeID());
            pstmt.setInt(3, route.getTimeTaken());
            pstmt.setInt(4, route.getAccessibilityLevel());
            pstmt.setInt(5, route.getRouteID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating route: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE Users SET Name = ?, AccessibilityLevel = ? WHERE UserID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAccessibilityLevel());
            pstmt.setInt(3, user.getUserID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    public void updateCollege(College college) {
        String sql = "UPDATE Colleges SET CollegeName = ? WHERE CollegeID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, college.getCollegeName());
            pstmt.setInt(2, college.getCollegeID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating college: " + e.getMessage());
        }
    }

    // DELETE operations

    public void deleteAccessibilityLevel(int level) {
        String sql = "DELETE FROM AccessibilityLevels WHERE AccessibilityLevel = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, level);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting accessibility level: " + e.getMessage());
        }
    }

    public void deleteRoute(int routeID) {
        String sql = "DELETE FROM Connections WHERE RouteID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, routeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting route: " + e.getMessage());
        }
    }

    public void deleteUser(int userID) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    public void deleteCollege(int collegeID) {
        String sql = "DELETE FROM Colleges WHERE CollegeID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, collegeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting college: " + e.getMessage());
        }
    }
}