public class Node {
    int data; // Holds the integer data for this node
    Node next; // Reference to the next node in the list, null if there is no next node

    // Constructor that initializes a new Node with the given data.
    public Node(int data) {
        this.data = data; // Set the data field with the value passed to the constructor
        this.next = null; // Initialize the next field to null indicating the end of the list
    }
}