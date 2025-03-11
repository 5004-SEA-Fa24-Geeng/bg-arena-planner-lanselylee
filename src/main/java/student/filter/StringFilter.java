package student.filter;

import student.Game;
import student.GameData;
import student.Operations;

/**
 * Filter for string values (game names).
 */
public class StringFilter extends Filter {
    
    public StringFilter(GameData column, Operations operator, String value) {
        super(column, operator, value);
    }

    @Override
    public boolean apply(Game game) {
        String gameValue = game.getName();
        
        return switch (operator) {
            case EQUALS -> gameValue.equalsIgnoreCase(value);
            default -> throw new IllegalArgumentException("Invalid operator for string comparison: " + operator);
        };
    }
} 