import java.util.ArrayList;

public class RouteFinder {
    // Inner class Node used to represent each college during the search.
    private class Node {
        int collegeID; // Stores the ID of the college at this node.
        Node parent; // Stores the parent node in the search tree, used for backtracking.

        // Constructor for Node, sets the college ID and the parent node.
        public Node(int collegeID, Node parent) {
            this.collegeID = collegeID; // Assigns the college ID to this node.
            this.parent = parent; // Sets the parent of this node.
        }
    }

    // Main method to find the optimal route between two colleges using BFS.
    public ArrayList<Integer> findOptimalRoute(int startCollegeID, int endCollegeID, int userAccessibilityLevel, CustomHashMap collegesMap) {
        // Custom queue to store nodes to visit.
        CustomLinkedListQueue<Node> queue = new CustomLinkedListQueue<>();
        // Custom hashmap to keep track of visited nodes.
        CustomHashMap visited = new CustomHashMap();

        // Starting node for BFS.
        Node startNode = new Node(startCollegeID, null);
        // Enqueue the start node.
        queue.enqueue(startNode);
        // Mark the start node as visited.
        visited.add(startCollegeID, startNode);

        // Loop continues until there are no more nodes to visit.
        while (!queue.isEmpty()) {
            // Dequeue the next node to visit.
            Node currentNode = queue.dequeue();
            // Get the current college's information from the map.
            College currentCollege = (College) collegesMap.get(currentNode.collegeID);

            // Check if the current node is the destination.
            if (currentNode.collegeID == endCollegeID) {
                // Reconstruct and return the path from the destination to the start.
                return reconstructPath(currentNode);
            }

            // Get all routes from the current college.
            ArrayList<Route> routes = currentCollege.getRoutes();
            // Examine each route from the current college.
            for (Route route : routes) {
                // Skip routes that don't meet the user's accessibility level.
                if (route.getAccessibilityLevel() < userAccessibilityLevel) {
                    continue;
                }

                // Get the ID of the college at the end of the route.
                int neighbourID = route.getEndCollegeID();
                // Check if the end college has not been visited.
                if (!visited.contains(neighbourID)) {
                    // If not visited, create a new node for this neighbor.
                    Node neighbourNode = new Node(neighbourID, currentNode);
                    // Mark the neighbor as visited.
                    visited.add(neighbourID, neighbourNode);
                    // Enqueue the neighbor node for future visits.
                    queue.enqueue(neighbourNode);
                }
            }
        }

        // If the destination was not reached, return an empty path.
        return new ArrayList<>();
    }

    // Reconstructs the path from the end node back to the start node.
    private ArrayList<Integer> reconstructPath(Node endNode) {
        // Create an array list to hold the path.
        ArrayList<Integer> path = new ArrayList<>();
        // Start with the end node.
        Node currentNode = endNode;

        // Backtrack from the end node to the start node.
        while (currentNode != null) {
            // Insert the college ID at the beginning of the path.
            path.add(0, currentNode.collegeID);
            // Move to the parent node.
            currentNode = currentNode.parent;
        }

        // Return the reconstructed path.
        return path;
    }
}