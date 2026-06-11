# Cambridge Campus Mapper

A-level Computer Science NEA (Sep 2023 - Jan 2024). Java route-finder over a graph of Cambridge University colleges, with custom-built data structures and per-user accessibility-level constraints.

## How it works

The campus is modelled as a graph: colleges are nodes, walkable connections between them are edges, each edge has an accessibility level (1 = easy access, 5 = stairs/uneven only). A user has their own accessibility level; the route finder will not cross an edge whose required level is higher than the user's.

The search itself is BFS:

1. Start node is the source college; enqueue it
2. Pop a node; look up its neighbours in the colleges hash map; skip neighbours we have already visited; skip edges the user cannot use
3. Enqueue every viable neighbour with the popped node set as its parent
4. When the destination is popped, walk back through `parent` pointers to reconstruct the path
5. If the queue empties without finding the destination, no accessible route exists

Because BFS explores nodes in order of edge-count, the first path found is the one with the fewest college-to-college hops, which is the proxy for shortest used in the NEA spec.

## Custom data structures

- `CustomHashMap` - separate-chaining hash map keyed by integer college ID, holding college objects. Used for O(1) college lookup during BFS and for the visited set.
- `CustomLinkedListQueue` - generic linked-list queue with `enqueue` / `dequeue` / `isEmpty`. Used as the BFS frontier.

Both were written from scratch (mark scheme requirement) rather than using `java.util.HashMap` or `LinkedList`.

## Data

- `Colleges.csv` - college IDs and names
- `Connections.csv` - edges between colleges with required accessibility level
- `AccessibilityLevels.csv` - human-readable labels for levels 1-5
- `Users.csv` - sample users with their accessibility level
- `UniversityMapper.db` - the same data in SQLite, used by `DatabaseManager`

## Running

```
javac -cp "lib/sqlite-jdbc.jar" src/*.java -d out
java -cp "out;lib/sqlite-jdbc.jar" Main
```

Drop `sqlite-jdbc.jar` (any 3.x) into `lib/` first. The DB path is `data/UniversityMapper.db`.

The menu lets you manage colleges, routes, users and accessibility levels, plus run a route lookup between any two colleges for any user.
