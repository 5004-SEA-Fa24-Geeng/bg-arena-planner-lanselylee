package student;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import student.Planner;
import student.IPlanner;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * JUnit test for the Planner class.
 * 
 * Just a sample test to get you started, also using
 * setup to help out. 
 */
public class PlannerTest {
    static Set<BoardGame> games;

    @BeforeAll
    public static void setup() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

     @Test
    public void testFilterName() {
        IPlanner planner = new Planner(games);
        
        // Test exact match
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
        
        // Test contains
        filtered = planner.filter("name ~= Go").toList();
        assertEquals(4, filtered.size());
        Set<String> expectedNames = Set.of("Go", "Go Fish", "golang", "GoRami");
        Set<String> actualNames = filtered.stream()
            .map(BoardGame::getName)
            .collect(Collectors.toSet());
        assertEquals(expectedNames, actualNames);
        
        // Test greater than
        filtered = planner.filter("name > Go").toList();
        List<String> actualNames2 = filtered.stream()
            .map(BoardGame::getName)
            .sorted()
            .collect(Collectors.toList());
        
        List<String> expectedNames2 = List.of("Go Fish", "GoRami", "Monopoly", "Tucano", "golang");
        assertEquals(expectedNames2, actualNames2);
    }
    
    @Test
    public void testFilterNumericGreaterThan() {
        IPlanner planner = new Planner(games);
        
        // Test greater than for minPlayers
        List<BoardGame> filtered = planner.filter("minPlayers>5").toList();
        assertEquals(3, filtered.size());
        
        // Convert to set of names for easier comparison
        Set<String> actualNames = filtered.stream()
            .map(BoardGame::getName)
            .collect(Collectors.toSet());
        
        Set<String> expectedNames = Set.of("GoRami", "Monopoly", "Tucano");
        assertEquals(expectedNames, actualNames);
        
        // Test the output is sorted by name by default
        List<String> sortedNames = filtered.stream()
            .map(BoardGame::getName)
            .collect(Collectors.toList());
        assertEquals(List.of("GoRami", "Monopoly", "Tucano"), sortedNames);
    }

    @Test
    public void testFilterNumericCaseInsensitive() {
        IPlanner planner = new Planner(games);
        
        // Test case-insensitive numeric filter
        List<BoardGame> filtered = planner.filter("min_Players>1").toList();
        assertEquals(7, filtered.size());
        
        // Convert to set of names for easier comparison
        Set<String> actualNames = filtered.stream()
            .map(BoardGame::getName)
            .collect(Collectors.toSet());
        
        Set<String> expectedNames = Set.of("Chess", "Go", "Go Fish", "golang", 
                                         "GoRami", "Monopoly", "Tucano");
        assertEquals(expectedNames, actualNames);
        
        // Test with different case
        filtered = planner.filter("MinPlayers>5").toList();
        actualNames = filtered.stream()
            .map(BoardGame::getName)
            .collect(Collectors.toSet());
        
        expectedNames = Set.of("GoRami", "Monopoly", "Tucano");
        assertEquals(expectedNames, actualNames);
    }
}