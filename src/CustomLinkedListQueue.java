public class CustomLinkedListQueue<T> {
    // A nested static class for QueueNode. Static because it doesn't need access to the outer class's fields.
    private static class QueueNode<T> {
        T data; // The data to be stored in the node. It's of generic type T.
        QueueNode<T> next; // Reference to the next node in the queue, also of generic type T.

        // Constructor for QueueNode. It initializes the node with the provided data and sets the next node to null.
        public QueueNode(T data) {
            this.data = data; // Set the data of this node.
            this.next = null; // Initialize the next node as null, as this is the end of the queue when created.
        }
    }

    private QueueNode<T> head; // The front of the queue, where items are dequeued from.
    private QueueNode<T> tail; // The end of the queue, where items are enqueued.

    // Constructor for CustomLinkedListQueue. It initializes the head and tail as null, indicating an empty queue.
    public CustomLinkedListQueue() {
        this.head = this.tail = null; // Both head and tail are set to null, indicating the queue is empty.
    }

    // Method to add an item to the queue. It accepts data of generic type T.
    public void enqueue(T data) {
        QueueNode<T> newNode = new QueueNode<>(data); // Create a new QueueNode with the provided data.
        if (this.tail == null) { // If the queue is empty (tail is null), then the new node becomes both the head and tail.
            this.head = this.tail = newNode; // Set both head and tail to the new node.
            return; // Exit the method here as we've added the first element to the queue.
        }
        // If the queue is not empty, the new node is appended after the current tail.
        this.tail.next = newNode; // Point the current tail's next to the new node.
        this.tail = newNode; // Update the tail to be the new node.
    }

    // Method to remove an item from the front of the queue.
    public T dequeue() {
        if (this.head == null) { // If the queue is empty (head is null), throw an exception.
            throw new IllegalStateException("Queue is empty");
        }
        QueueNode<T> temp = this.head; // Store the current head in a temporary variable.
        this.head = this.head.next; // Move the head to the next node in the queue.
        if (this.head == null) { // If the queue is now empty (head is null), also set tail to null.
            this.tail = null; // Ensure the tail is also null to maintain the empty state.
        }
        return temp.data; // Return the data from the node that was at the front of the queue.
    }

    // Method to check if the queue is empty by checking if the head is null.
    public boolean isEmpty() {
        return this.head == null; // Return true if head is null, false otherwise.
    }

    // Method to get the data from the front of the queue without removing the node.
    public T peek() {
        if (this.head == null) { // If the queue is empty (head is null), throw an exception.
            throw new IllegalStateException("Queue is empty");
        }
        return this.head.data; // Return the data from the node at the front of the queue.
    }
}