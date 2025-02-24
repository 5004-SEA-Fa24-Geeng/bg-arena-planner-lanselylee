# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.
## (INITIAL DESIGN): Class Diagram


```mermaid
classDiagram
    class IGameList {
        <<interface>>
        +List<String> getGameNames()
        +void clear()
        +int count()
        +void saveGame(String filename)
        +void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException
        +void removeFromList(String str) throws IllegalArgumentException
    }
    
    class IPlanner {
        <<interface>>
        +Stream<BoardGame> filter(String filter)
        +Stream<BoardGame> filter(String filter, GameData sortOn)
        +Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending)
        +void reset()
    }

    class BoardGame {
        +String getName()
        +String getCategory()
        +int getMinPlayers()
        +int getMaxPlayers()
    }

    IGameList <|.. GameList
    IPlanner <|.. Planner
```



### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces.
```mermaid
classDiagram
    class GameList {
        -Set<BoardGame> games
        +GameList()
        +List<String> getGameNames()
        +void clear()
        +int count()
        +void saveGame(String filename)
        +void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException
        +void removeFromList(String str) throws IllegalArgumentException
    }

    class Planner {
        -Set<BoardGame> allGames
        -List<BoardGame> currentFilteredGames
        +Planner(Set<BoardGame> games)
        +Stream<BoardGame> filter(String filter)
        +Stream<BoardGame> filter(String filter, GameData sortOn)
        +Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending)
        +void reset()
    }

    class GameFilter {
        +Stream<BoardGame> filterByCategory(Stream<BoardGame> games, String category)
        +Stream<BoardGame> filterByPlayerCount(Stream<BoardGame> games, int players)
    }

    IGameList <|.. GameList
    IPlanner <|.. Planner
    Planner --> GameFilter : uses
```






## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

### **Planned Test Cases**
In Test-Driven Development (TDD), we define test cases first, then write code to satisfy these tests.

#### **（1️1） `GameList` Related Tests**
- **`getGameNames()`**
  - Ensure the returned list of game names is **sorted alphabetically** (case-insensitive).
  - Ensure the list **does not contain duplicate games**.

- **`addToList()`**
  - Add a game by **name** and verify that `count()` updates correctly.
  - Add a game using an **index** (e.g., `"1"`, `"2-4"`) and check if the correct games are added.
  - Use `"all"` to add all games and verify the list contains all expected games.
  - Pass invalid inputs (e.g., `"abc"`, `"10-5"`) and ensure `IllegalArgumentException` is thrown.

- **`removeFromList()`**
  - Remove a game by **name** and verify that `count()` updates correctly.
  - Remove a game by **index** (e.g., `"1"`, `"2-4"`) and check if the correct games are removed.
  - Use `"all"` to clear the list and ensure `count() == 0`.
  - Pass invalid inputs (e.g., `"xyz"`) and ensure `IllegalArgumentException` is thrown.

- **`saveGame()`**
  - Save the game list to a file and verify the file is written correctly.
  - Read the file contents and ensure they match the sorted order of `getGameNames()`.

#### **（2️） `Planner` Related Tests**
- **`filter()`**
  - Filter games by **minimum player count** (e.g., `minPlayers>4`) and verify results.
  - Filter games by **name** (e.g., `name~=chess`) and check if the correct games are returned.
  - Apply **multiple conditions** (e.g., `minPlayers>4,maxPlayers<6`) and verify that games meet both criteria.
  - Apply **multiple filters on the same field** (e.g., `minPlayers>4,minPlayers<6`) and check if results are correct.
  - Pass an invalid filter format and ensure an exception is thrown.

- **`reset()`**
  - After calling `reset()`, verify that all games return to the **unfiltered state**.




## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 
