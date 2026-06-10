import java.util.ArrayList;
import java.util.List;

public class CustomHashMap {
    private Element[] table; // Array to hold the hash map elements.
    private int size; // Variable to keep track of the number of elements in the hash map.
    private final static int INITIAL_CAPACITY = 11; // Initial capacity of the hash map.
    private final static float LOAD_FACTOR_THRESHOLD = 0.75f; // Load factor after which we resize the hash map.

    // Constructor to initialize the hash map.
    public CustomHashMap() {
        table = new Element[INITIAL_CAPACITY]; // Initializing the table with the initial capacity.
        size = 0; // Initializing size to zero.
    }

    // Method to add a key-value pair to the hash map.
    public void add(int key, Object value) {
        if (contains(key)) { // Check if the key already exists in the map.
            throw new IllegalArgumentException("Key already exists."); // Throw an exception if key exists.
        }

        if (loadFactor() >= LOAD_FACTOR_THRESHOLD) { // Check if the load factor threshold is exceeded.
            resize(); // Resize the hash map if the threshold is exceeded.
        }

        int index = hash(key, table.length); // Calculate the index for the key using the hash function.

        // This while loop deals with collisions. If the calculated index is already occupied,
        // it tries the next index until it finds an empty spot.
        while (table[index] != null) {
            // Increment the index and wrap around the array if needed, to find next available slot in the hash table.
            // which is what the modulus operation with table.length ensures.
            index = (index + 1) % table.length;

        }

        table[index] = new Element(key, value); // Store the new element at the calculated index.
        size++; // Increment the size of the hash map.
    }

    // Method to resize the hash map when load factor threshold is reached.
    private void resize() {
        int newSize = table.length * 2; // Double the size of the hash map.
        Element[] newTable = new Element[newSize]; // Create a new table with the new size.

        // Rehash all the existing elements in the old table to the new table.
        for (Element element : table) {
            if (element != null) {
                int index = hash(element.getKey(), newSize); // Calculate new index for each element.
                // Collision resolution in the new table.
                while (newTable[index] != null) {
                    index = (index + 1) % newSize; // Find the next available index.
                }
                newTable[index] = element; // Place the element at the new index in the new table.
            }
        }

        table = newTable; // Replace the old table with the new table.
    }

    // Method to calculate the load factor of the hash map.
    private float loadFactor() {
        return (float) size / table.length; // Current size divided by the capacity of the hash map.
    }

    // The hash() method calculates an array index for a given key.
    private int hash(int key, int capacity) {
        // The modulus operator (%) returns the remainder of dividing 'key' by 'capacity'.
        // Also ensures that the hash value is always within the bounds of the array.
        return key % capacity;
    }


    // The delete() method removes a key-value pair based on the key.
    public void delete(int key) {
        int index = hash(key); // Calculate initial index for the key.
        // This while loop finds the exact location of the element to delete.
        // It continues until it finds an empty spot or the key.
        while (table[index] != null && table[index].getKey() != key) {
            // It moves to the next index using linear probing, wrapping around the array.
            index = (index + 1) % table.length;
        }
        if (table[index] != null) { // If the particular index in that table is not equal to null, so has something in it.
            table[index] = null; // Remove the element from the hash map.
            size--; // Decrement the size.
        }
    }

    // The get() method retrieves the value associated with a key.
    public Object get(int key) {
        int index = hash(key); // Calculate initial index for the key.
        // This loop is similar to the one in delete(). It uses linear probing to find the key.
        while (table[index] != null && table[index].getKey() != key) {
            // Increment index and wrap around if necessary.
            index = (index + 1) % table.length;
        }
        // If it's not null, it gets the value. Otherwise, it returns null.
        if (table[index] != null) {
            return table[index].getValue();
        } else {
            return null;
        }
    }


    // The contains() method checks if a key is in the hash map.
    public boolean contains(int key) {
        int index = hash(key); // Calculate initial index for the key.
        // This loop searches through the array for the key.
        while (table[index] != null) {
            // If the key at 'index' matches the one we're looking for, we return true.
            if (table[index].getKey() == key) {
                return true;
            }
            // Move to the next index and wrap around the array if needed.
            index = (index + 1) % table.length;
        }
        // If we exit the loop without finding the key, we return false.
        return false;
    }

    // Checks if the hash map is empty by iterating over the table array.
    public boolean isEmpty() {
        for (Element element : table) { // Iterate through all elements in the table array.
            if (element != null) { // If any element is not null, the table is not empty.
                return false; // Return false indicating the hash map is not empty.
            }
        }
        return true; // Return true indicating the hash map is empty after checking all elements.
    }

    // Returns a list of all key-value pairs in the hash map.
    public List<Entry> entrySet() {
        List<Entry> entries = new ArrayList<>(); // Create a new ArrayList to store entries.
        for (Element element : table) { // Iterate through all elements in the table.
            if (element != null) { // If an element is not null,
                entries.add(new Entry(element.getKey(), element.getValue())); // add it to the list as a new Entry.
            }
        }
        return entries; // Return the list of entries.
    }

    // Returns the internal array of elements (table).
    public Element[] getElements() {
        return table; // Return the table array representing the hash map.
    }

    // Static inner class representing an entry in the hash map (key-value pair).
    public static class Entry {
        private int key; // The key of the entry.
        private Object value; // The value associated with the key.

        // Constructor for Entry class.
        public Entry(int key, Object value) {
            this.key = key; // Initialize the key.
            this.value = value; // Initialize the value.
        }

        // Returns the key of the entry.
        public int getKey() {
            return key; // Return the key.
        }

        // Returns the value of the entry.
        public Object getValue() {
            return value; // Return the value.
        }
    }

    // Hash function to calculate the index for a key.
    private int hash(int key) {
        return key % table.length; // Modulo operation to fit the hash within the table array bounds.
    }

    // The Element class represents a key-value pair in the hash map.
    // 'static' because it does not need to access any instance variables of the outer CustomHashMap class.
    static class Element {
        private int key; // The key, used to identify the value.
        private Object value; // The value, associated with the key.

        // Constructor initializes the key and value when a new Element is created.
        public Element(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        // getKey() returns the key of this element.
        public int getKey() {
            return key;
        }

        // getValue() returns the value associated with this element's key.
        public Object getValue() {
            return value;
        }
    }
}