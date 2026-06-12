# campus mapper

my a-level computer science final project (sep 2023 to jan 2024).

a java program that finds the shortest walking route between two cambridge colleges. each user has an accessibility level (how mobile they are) and the program only uses paths they can actually walk.

## how it works

i store the colleges and the paths between them as a graph in a sqlite database. each path has a required accessibility level (1 is easy access, 5 means stairs).

to find the shortest route i use a search called **bfs** (breadth first search):

1. start at the source college and look at all the colleges directly connected to it
2. then look at all the colleges connected to those (without re-visiting any)
3. carry on until i reach the destination
4. then trace back through the path to print the route

bfs finds the route with the fewest steps, which is what counts as "shortest" in this project.

## the from-scratch parts

the exam mark scheme said i had to write my own data structures. so instead of using java's built in `HashMap` and `LinkedList`, i wrote my own:

- `CustomHashMap` - looks up a college from its id in one step
- `CustomLinkedListQueue` - the queue bfs uses to remember which college to visit next

## files

- `src/` - the java files
- `data/` - csvs for colleges, paths, users, accessibility levels, plus a sqlite database with the same data

## run

```
javac -cp "lib/sqlite-jdbc.jar" src/*.java -d out
java -cp "out;lib/sqlite-jdbc.jar" Main
```

drop `sqlite-jdbc.jar` (any 3.x version) into a `lib/` folder first. then use the menu to add colleges, set up users, or run a route lookup.
