# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 


## Technical Questions

### 1. What is the difference between `==` and `.equals()` in Java?  
In Java, `==` checks whether two object **references** point to the same memory location, while `.equals()` checks whether two objects are **logically equal**.

Example:
```java
String s1 = new String("hello");
String s2 = new String("hello");

System.out.println(s1 == s2);      // false (different memory locations)
System.out.println(s1.equals(s2)); // true  (same content)



2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 
You can use Collections.sort() with String.CASE_INSENSITIVE_ORDER:
List<String> words = Arrays.asList("Banana", "apple", "Cherry");
Collections.sort(words, String.CASE_INSENSITIVE_ORDER);
System.out.println(words); // Output: [apple, Banana, Cherry]
This ensures "apple" comes before "Banana" (ignoring case).




3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 
Yes, the order matters. Example:
String str = ">=";
if (str.contains(">=")) {
    System.out.println("GREATER_THAN_EQUALS");
} else if (str.contains(">")) {
    System.out.println("GREATER_THAN");
}

and the outpot will be:
GREATER_THAN_EQUALS

If we check str.contains(">") first, it would match ">=" too early and return "GREATER_THAN" instead of "GREATER_THAN_EQUALS".

Similarly, for "<=", checking "<" first would cause incorrect behavior.



4. What is the difference between a List and a Set in Java? When would you use one over the other? 


List (e.g., ArrayList): Allows duplicates, maintains insertion order.
Use case: When order is important (e.g., game history).
Set (e.g., HashSet, TreeSet): No duplicates, does not maintain insertion order.
Use case: When uniqueness is required (e.g., storing unique game names).
TreeSet keeps elements sorted.

5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 
A Map<K, V> stores key-value pairs. Example:
Map<String, Integer> gameMap = new HashMap<>();
gameMap.put("Chess", 2);
gameMap.put("Poker", 4);
n GamesLoader.java, a Map helps map column names to their indexes, making it easy to retrieve data dynamically.

Different types of Map:

HashMap: Fast lookups (O(1)), but no order guarantee.
TreeMap: Sorted by keys, useful when order matters.


6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?
An enum is a fixed set of named constants. Example:
enum GameType { BOARD, CARD, VIDEO }
GameType type = GameType.BOARD;
Advantages of enum:

Type safety (e.g., no invalid values).
Improves readability.
Works well with switch statements.
In GameData.java, enum is used for column name mappings to improve code clarity and safety.


7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```if (ct == CMD_QUESTION || ct == CMD_HELP) {
    processHelp();
} else if (ct == INVALID) {
    CONSOLE.printf("%s%n", ConsoleText.INVALID);
} else {
    CONSOLE.printf("%s%n", ConsoleText.INVALID);
}

    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
I would modify `console.properties` as follows:

```properties
welcome.message = Bienvenue sur Board Game Arena Planner!

// your consoles output here

After running the program:
Bienvenue sur Board Game Arena Planner!

```

Now, thinking about localization - we have the question of why does it matter? 

Localization (L10N) allows software to be adapted to different languages and cultures. This improves user experience and expands the market reach.

Benefits of localization:
Global market expansion – More users can use the software.
Better accessibility – Users can interact in their native language.
Cultural adaptation – Avoids misunderstandings caused by language differences.

The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? 
Dangers of poor localization:
Translation errors (e.g., machine translation mistakes).
Cultural insensitivity (e.g., colors, symbols have different meanings).
Technical issues (e.g., text expansion breaking UI).


Can you find any examples of that? 
For example, in 2009, HSBC Bank's slogan "Assume Nothing" was mistranslated in some languages as "Do Nothing", which cost millions in rebranding efforts.

Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?
How to improve localization?
Use professional translators instead of machine translation.
Store text in external resource files (like console.properties).
Test with native speakers before launching in a new market.
Follow Unicode standards (e.g., UTF-8) to support different languages.

References:

"Why Localization Matters" - Nielsen Norman Group (https://www.nngroup.com/articles/international-users/)
HSBC Rebranding Case - Business Insider (https://www.businessinsider.com/hsbc-global-branding-mistake-2019-11)
markdown


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 
