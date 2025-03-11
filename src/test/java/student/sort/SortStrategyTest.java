package student.sort;

import org.junit.jupiter.api.Test;
import student.Game;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SortStrategyTest {
    private final Game game1 = new Game("Catan", 3, 4, 60, 4.5, 2.0);
    private final Game game2 = new Game("Azul", 2, 4, 45, 4.8, 1.5);
    private final Game game3 = new Game("Chess", 2, 2, 45, 4.9, 3.0);

    @Test
    public void testNameSortAscending() {
        GameSortStrategy strategy = SortStrategyFactory.createSortStrategy("name:asc");
        List<Game> games = Arrays.asList(game1, game2, game3);
        games.sort(strategy.getComparator());
        
        assertEquals("Azul", games.get(0).getName());
        assertEquals("Catan", games.get(1).getName());
        assertEquals("Chess", games.get(2).getName());
    }

    @Test
    public void testRatingDescending() {
        GameSortStrategy strategy = SortStrategyFactory.createSortStrategy("rating:desc");
        List<Game> games = Arrays.asList(game1, game2, game3);
        games.sort(strategy.getComparator());
        
        assertEquals(4.9, games.get(0).getRating(), 0.01);
        assertEquals(4.8, games.get(1).getRating(), 0.01);
        assertEquals(4.5, games.get(2).getRating(), 0.01);
    }

    @Test
    public void testInvalidSortString() {
        assertThrows(IllegalArgumentException.class, () -> 
            SortStrategyFactory.createSortStrategy("invalid_sort")
        );
    }
} 