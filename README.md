# campus mapper

my a-level computer science nea (sep 2023 to jan 2024).

a java program that finds the shortest route between two cambridge colleges. each user has an accessibility level and the program only uses paths they can actually walk.

## how it works

the campus is a graph: colleges are nodes, paths between them are edges, each edge has a required accessibility level.

to find a route i use bfs (breadth first search):

1. start at the source college
2. look at all connected colleges and add the ones we havent visited to a queue
3. pop the next one, do the same
4. when we reach the destination, follow the parent pointers back to get the route

i wrote my own hash map and my own queue (the mark scheme required custom data structures, not the ones from `java.util`).

## files

- `src/` - the java files
- `data/` - the csv files for colleges, connections, users, accessibility levels, plus a sqlite db with the same data

## run

```
javac -cp "lib/sqlite-jdbc.jar" src/*.java -d out
java -cp "out;lib/sqlite-jdbc.jar" Main
```

drop `sqlite-jdbc.jar` into `lib/` first. then use the menu to add colleges, set up users, or run a route lookup.
