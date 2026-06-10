import java.util.ArrayList;

public class College {
    private int collegeID; // Declare a private integer for the college's unique identifier
    private String collegeName; // Declare a private string for the college's name
    private ArrayList neighbours; // Declare an ArrayList to store the names of neighbouring colleges (type is not specified)
    private ArrayList routes;  // Declare an ArrayList to store Route objects associated with this college

    // Constructor for the College class that initializes the collegeID and collegeName.
    public College(int collegeID, String collegeName) {
        this.collegeID = collegeID; // Set the collegeID field with the provided argument
        this.collegeName = collegeName; // Set the collegeName field with the provided argument
        this.neighbours = new ArrayList(); // Initialize the neighbours list without specifying a type
        this.routes = new ArrayList(); // Initialize the routes list
    }

    // Public getter method for collegeID.
    public int getCollegeID() {
        return collegeID; // Return the collegeID of the college
    }

    // Public setter method for collegeID.
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID; // Update the collegeID field with the provided argument
    }

    // Public getter method for collegeName.
    public String getCollegeName() {
        return collegeName; // Return the name of the college
    }

    // Public setter method for collegeName.
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName; // Update the collegeName field with the provided argument
    }

    // Method to add a neighbour to the college if it's not already in the list.
    public void addNeighbour(String neighbourName) {
        if (!neighbours.contains(neighbourName)) { // Check if the neighbour is not already in the list
            neighbours.add(neighbourName); // Add the neighbour to the list
        }
    }

    // Public getter method for the list of neighbours.
    public ArrayList getNeighbours() {
        return neighbours; // Return the list of neighbours
    }

    // Public setter method for the list of neighbours.
    public void setNeighbours(ArrayList neighbours) {
        this.neighbours = neighbours; // Set the neighbours list with the provided list
    }

    // Method to add a Route object to the list of routes.
    public void addRoute(Route route) {
        this.routes.add(route); // Add the route to the list of routes
    }

    // Public getter method for the list of routes.
    public ArrayList getRoutes() {
        return this.routes; // Return the list of routes
    }

    // Override the default toString method to provide a string representation of the College object.
    @Override
    public String toString() {
        return "College{" +
                "collegeID=" + collegeID + // Include the collegeID in the string representation
                ", collegeName='" + collegeName + '\'' + // Include the collegeName in the string representation
                ", neighbours=" + neighbours + // Include the list of neighbours in the string representation
                '}';
    }
}