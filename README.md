# Campus Mapper

My A-level computer science project (September 2023 to January 2024). It is a Java program that finds the quickest walking route between two Cambridge colleges. Each user has an accessibility level, and the program only uses paths they can actually take.

## How it works

The campus is stored as a graph: colleges are points, paths between them are connections, and each connection has a walking time and a required accessibility level. The data lives in a SQLite database.

To find the quickest route it uses **Dijkstra's algorithm**. The idea is simple:

1. Give every college a label showing the shortest time found so far to reach it from the start. The start is 0, everything else begins at infinity.
2. Pick the unvisited college with the smallest label and treat its label as final.
3. Look at its neighbours. If going through the current college is quicker than a neighbour's current label, lower that neighbour's label.
4. Repeat until the destination's label is final, then trace the labels backwards to read off the route.

## The from-scratch parts

The exam required me to write my own data structures rather than use Java's built-in ones. So the program contains:

- `CustomHashMap` — looks up a college from its ID instantly.
- `CustomLinkedListQueue` — holds the colleges still waiting to be processed.

## Files

- `src/` holds the Java files.
- `data/` holds the CSV files for colleges, paths, users and accessibility levels, plus a SQLite database with the same data.

## Run it

```
javac -cp "lib/sqlite-jdbc.jar" src/*.java -d out
java -cp "out;lib/sqlite-jdbc.jar" Main
```

Put `sqlite-jdbc.jar` (any 3.x version) in a `lib/` folder first. Then use the menu to add colleges, set up users, or run a route lookup.

You can also try a live version of the route finder at [birkaran.com/projects/campus-mapper](https://birkaran.com/projects/campus-mapper).
