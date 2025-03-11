package student.filter;

import org.junit.jupiter.api.Test;
import student.Game;
import student.GameData;
import student.Operations;
import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    // Concrete implementation of Filter for testing
    private static class TestFilter extends Filter {
        public TestFilter(GameData column, Operations operator, String value) {
            super(column, operator, value);
        }

        @Override
        public boolean apply(Game game) {
            String gameValue = switch (column) {
                case NAME -> game.getName();
                case RATING -> String.valueOf(game.getRating());
                // Add other cases as needed
                default -> "";
            };
            
            switch (operator) {
                case EQUALS:
                    return gameValue.equals(value);
                case GREATER_THAN:
                    return gameValue.compareTo(value) > 0;
                case LESS_THAN:
                    return gameValue.compareTo(value) < 0;
                default:
                    return false;
            }
        }
    }

    @Test
    void testFilterConstructor() {
        Filter filter = new TestFilter(GameData.NAME, Operations.EQUALS, "Test Game");
        
        // Use reflection to test protected fields
        try {
            var columnField = Filter.class.getDeclaredField("column");
            var operatorField = Filter.class.getDeclaredField("operator");
            var valueField = Filter.class.getDeclaredField("value");
            
            columnField.setAccessible(true);
            operatorField.setAccessible(true);
            valueField.setAccessible(true);
            
            assertEquals(GameData.NAME, columnField.get(filter));
            assertEquals(Operations.EQUALS, operatorField.get(filter));
            assertEquals("Test Game", valueField.get(filter));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to access Filter fields: " + e.getMessage());
        }
    }

    @Test
    void testEqualsOperator() {
        Filter filter = new TestFilter(GameData.NAME, Operations.EQUALS, "Test Game");
        
        Game matchingGame = new Game("Test Game", 2, 4, 60, 8.0, 2.5);
        Game nonMatchingGame = new Game("Different Game", 2, 4, 60, 8.0, 2.5);
        
        assertTrue(filter.apply(matchingGame));
        assertFalse(filter.apply(nonMatchingGame));
    }

    @Test
    void testGreaterThanOperator() {
        Filter filter = new TestFilter(GameData.RATING, Operations.GREATER_THAN, "7");
        
        Game highRatedGame = new Game("High Rated Game", 2, 4, 60, 8.0, 2.5);
        Game lowRatedGame = new Game("Low Rated Game", 2, 4, 60, 6.0, 2.5);
        
        assertTrue(filter.apply(highRatedGame));
        assertFalse(filter.apply(lowRatedGame));
    }

    @Test
    void testLessThanOperator() {
        Filter filter = new TestFilter(GameData.RATING, Operations.LESS_THAN, "7");
        
        Game highRatedGame = new Game("High Rated Game", 2, 4, 60, 8.0, 2.5);
        Game lowRatedGame = new Game("Low Rated Game", 2, 4, 60, 6.0, 2.5);
        
        assertFalse(filter.apply(highRatedGame));
        assertTrue(filter.apply(lowRatedGame));
    }
} 