public class AccessibilityLevel {
    private int level; // Declare a private integer variable to store the accessibility level.
    private String description; // Declare a private string variable to store the description of the accessibility level.

    // Constructor for the AccessibilityLevel class that initializes the level and description.
    public AccessibilityLevel(int level, String description) {
        this.level = level; // Assign the input level parameter to the level field of the class.
        this.description = description; // Assign the input description parameter to the description field of the class.
    }

    // Public getter method to retrieve the level of accessibility.
    public int getLevel() {
        return level; // Return the current value of the level field.
    }

    // Public setter method to update the level of accessibility.
    public void setLevel(int level) {
        this.level = level; // Update the level field with the new level provided as an argument.
    }

    // Public getter method to retrieve the description of the accessibility level.
    public String getDescription() {
        return description; // Return the current value of the description field.
    }

    // Public setter method to update the description of the accessibility level.
    public void setDescription(String description) {
        this.description = description; // Update the description field with the new description provided as an argument.
    }

    // Override the default toString method to provide a string representation of the AccessibilityLevel object.
    @Override
    public String toString() {
        return "AccessibilityLevel{" +
                "level=" + level + // Include the level in the string representation.
                ", description='" + description + '\'' + // Include the description in the string representation.
                '}';
    }
}