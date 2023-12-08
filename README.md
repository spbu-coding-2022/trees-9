# Trees by team 9
A project created to study the mechanics of AVL, Red-Black and BST trees, unit tests, databases and graphic user interface
![image](https://github.com/spbu-coding-2022/trees-9/assets/111463436/930cd060-9e55-42db-9967-cf8ff1e3081b)


## Navigation

- [Download and build](#download-and-build)
- [Trees](#trees)
    - [Unit tests](#unit-tests-by-junit-5)
    - [Databases](#databases)
- [Authors](#authors)

## Download and build:
Possible way to clone project by SSH key in command line:

    git clone git@github.com:spbu-coding-2022/trees-9.git
    
You may build it by [gradle](https://gradle.org/):

    ./gradlew build

To run app:

    ./gradlew run

*Aware : build method depends on operating system*
## Trees
Implemented 3 basic tree:
- [Binary search](https://en.wikipedia.org/wiki/Binary_search_tree) tree
- [Red-Black](https://en.wikipedia.org/wiki/Red%E2%80%93black_tree) tree
- [AVL](https://en.wikipedia.org/wiki/AVL_tree) tree

Each tree support add, remove, find and print functions.
Node consist of **comparable** key and value
## Unit tests (by [JUnit 5](https://junit.org/junit5/))
Tests aimed at checking different cases:
- Find existing and not existing nodes
- Add up to 10000 nodes
- Special cases of balancing
- Remove existing and not existing nodes

For all trees, the corresponding validator is implemented

## Databases
3 different ways to store trees: SQLite, Neo4j and .csv files

Capability:
- Insert all nodes to tree
- Write all nodes to DB
- Delete itself

*It's okey if*: relationships of nodes changed when the application is restarted
 

## Authors
- gladiuswq - [github](https://github.com/gladiuswq), [contact](https://t.me/gladiuswq)  
- raf-nr - [github](https://github.com/raf-nr), [contact](https://t.me/nrrafik)  
- vacmannnn - [github](https://github.com/vacmannnn), [contact](https://t.me/vacmannnn) 
