# Cambridge Campus Mapper

A-level NEA project. Java route-finder over a SQLite graph of Cambridge colleges, with custom hash map and linked-list queue implementations and per-user accessibility levels.

## Run

```
javac -cp "lib/sqlite-jdbc.jar" src/*.java -d out
java -cp "out;lib/sqlite-jdbc.jar" Main
```

(Drop `sqlite-jdbc.jar` into `lib/` first.)

## Layout

```
src/    Java sources
data/   CSV inputs and SQLite DB
```
