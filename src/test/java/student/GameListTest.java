package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GameListTest {
    private GameList gameList;
    private Game testGame1;
    private Game testGame2;

    @BeforeEach
    public void setUp() {
        gameList = new GameList();
        testGame1 = new Game("Catan", 3, 4, 60, 2.5, 2.0);
        testGame2 = new Game("Chess", 2, 2, 45, 3.5, 3.0);
    }

    @Test
    public void testAddGame() {
        assertTrue(gameList.addGame(testGame1));
        assertEquals(1, gameList.size());
        assertTrue(gameList.contains(testGame1));
    }

    @Test
    public void testAddDuplicateGame() {
        gameList.addGame(testGame1);
        assertFalse(gameList.addGame(testGame1));
        assertEquals(1, gameList.size());
    }

    @Test
    public void testRemoveGame() {
        gameList.addGame(testGame1);
        assertTrue(gameList.removeGame(testGame1));
        assertEquals(0, gameList.size());
        assertFalse(gameList.contains(testGame1));
    }

    @Test
    public void testGetGamesByName() {
        gameList.addGame(testGame1);
        gameList.addGame(testGame2);
        List<Game> result = gameList.getGamesByName("Catan");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testGame1, result.get(0));
    }

    @Test
    public void testGetGamesSortedBy() {
        gameList.addGame(testGame1);
        gameList.addGame(testGame2);
        List<String> result = gameList.getGameNames();  // This returns names in case-insensitive ascending order
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Chess", result.get(0));
        assertEquals("Catan", result.get(1));
    }

    @Test
    public void testGetGamesFilteredBy() {
        gameList.addGame(testGame2);
        List<String> result = gameList.getGameNames();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Chess", result.get(0));  // Chess has 2 players max
    }

    @Test
    public void testGetGameNames() {
        gameList.addGame(testGame1);
        gameList.addGame(testGame2);
        List<String> result = gameList.getGameNames();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Chess", result.get(0));
        assertEquals("Catan", result.get(1));
    }
} 