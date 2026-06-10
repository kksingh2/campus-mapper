public class Route {
    private int routeID; // Stores the unique identifier for the route.
    private int startCollegeID; // Stores the ID of the college where the route starts.
    private int endCollegeID; // Stores the ID of the college where the route ends.
    private int timeTaken; // Stores the time taken to travel the route in minutes.
    private int accessibilityLevel; // Stores the accessibility level indicating suitability for various accessibility needs.

    // Constructor for the Route class that initializes the route's details.
    public Route(int routeID, int startCollegeID, int endCollegeID, int timeTaken, int accessibilityLevel) {
        this.routeID = routeID; // Set the route's ID.
        this.startCollegeID = startCollegeID; // Set the starting college's ID for this route.
        this.endCollegeID = endCollegeID; // Set the ending college's ID for this route.
        this.timeTaken = timeTaken; // Set the time taken to travel this route.
        this.accessibilityLevel = accessibilityLevel; // Set the accessibility level for this route.
    }

    // Getter method for routeID.
    public int getRouteID() {
        return routeID; // Return the route's ID.
    }

    // Setter method for routeID.
    public void setRouteID(int routeID) {
        this.routeID = routeID; // Update the route's ID.
    }

    // Getter method for startCollegeID.
    public int getStartCollegeID() {
        return startCollegeID; // Return the starting college's ID for this route.
    }

    // Setter method for startCollegeID.
    public void setStartCollegeID(int startCollegeID) {
        this.startCollegeID = startCollegeID; // Update the starting college's ID for this route.
    }

    // Getter method for endCollegeID.
    public int getEndCollegeID() {
        return endCollegeID; // Return the ending college's ID for this route.
    }

    // Setter method for endCollegeID.
    public void setEndCollegeID(int endCollegeID) {
        this.endCollegeID = endCollegeID; // Update the ending college's ID for this route.
    }

    // Getter method for timeTaken.
    public int getTimeTaken() {
        return timeTaken; // Return the time taken to travel this route.
    }

    // Setter method for timeTaken.
    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken; // Update the time taken to travel this route.
    }

    // Getter method for accessibilityLevel.
    public int getAccessibilityLevel() {
        return accessibilityLevel; // Return the accessibility level for this route.
    }

    // Setter method for accessibilityLevel.
    public void setAccessibilityLevel(int accessibilityLevel) {
        this.accessibilityLevel = accessibilityLevel; // Update the accessibility level for this route.
    }

    // Override the default toString method to provide a string representation of the Route object.
    @Override
    public String toString() {
        return "Route{" +
                "routeID = " + routeID + // Include the routeID in the string representation.
                ", startCollegeID = " + startCollegeID + // Include the startCollegeID in the string representation.
                ", endCollegeID = " + endCollegeID + // Include the endCollegeID in the string representation.
                ", timeTaken = " + timeTaken + // Include the timeTaken in the string representation.
                ", accessibilityLevel = " + accessibilityLevel + // Include the accessibilityLevel in the string representation.
                '}';
    }
}