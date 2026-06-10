import java.util.ArrayList; // Import the ArrayList class from the java.util package

public class User {
    private int userID; // Declare a private integer for the user's unique identifier
    private String name; // Declare a private string for the user's name
    private int accessibilityLevel; // Declare a private integer to represent the user's accessibility needs

    // Constructor for the User class that initializes the userID, name, and accessibilityLevel.
    public User(int userID, String name, int accessibilityLevel) {
        this.userID = userID; // Set the userID field with the provided userID argument
        this.name = name; // Set the name field with the provided name argument
        this.accessibilityLevel = accessibilityLevel; // Set the accessibilityLevel field with the provided accessibilityLevel argument
    }

    // Public getter method for userID.
    public int getUserID() {
        return userID; // Return the userID of the user
    }

    // Public setter method for userID.
    public void setUserID(int userID) {
        this.userID = userID; // Update the userID field with the provided userID argument
    }

    // Public getter method for name.
    public String getName() {
        return name; // Return the name of the user
    }

    // Public setter method for name.
    public void setName(String name) {
        this.name = name; // Update the name field with the provided name argument
    }

    // Public getter method for accessibilityLevel.
    public int getAccessibilityLevel() {
        return accessibilityLevel; // Return the accessibility level of the user
    }

    // Public setter method for accessibilityLevel.
    public void setAccessibilityLevel(int accessibilityLevel) {
        this.accessibilityLevel = accessibilityLevel; // Update the accessibilityLevel field with the provided accessibilityLevel argument
    }

    // Override the default toString method to provide a string representation of the User object.
    @Override
    public String toString() {
        return "User{" +
                "userID = " + userID + // Include the userID in the string representation
                ", name = '" + name + '\'' + // Include the name in the string representation
                ", accessibilityLevel = " + accessibilityLevel + // Include the accessibilityLevel in the string representation
                '}';
    }
}