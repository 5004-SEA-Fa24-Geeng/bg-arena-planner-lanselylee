package student.filter;

import student.Game;
import student.GameData;
import student.Operations;

/**
 * Filter for string values (game names).
 */
public class StringFilter extends Filter {
    
    /**
     * Constructs a StringFilter with the specified column, operator, and value.
     * @param column The game data column to filter on
     * @param operator The operation to apply in the filter
     * @param value The string value to compare against
     */
    public StringFilter(GameData column, Operations operator, String value) {
        super(column, operator, value);
    }

    /**
     * Applies the string filter to a game.
     * @param game The game to filter
     * @return true if the game matches the filter criteria, false otherwise
     */
    @Override
    public boolean apply(Game game) {
        String gameValue = game.getName();
        
        return switch (operator) {
            case EQUALS -> gameValue.equalsIgnoreCase(value);
            default -> throw new IllegalArgumentException("Invalid operator for string comparison: " + operator);
        };
    }
} 
