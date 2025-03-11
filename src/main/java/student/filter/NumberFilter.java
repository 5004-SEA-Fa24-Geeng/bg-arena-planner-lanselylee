package student.filter;

import student.Game;
import student.GameData;
import student.Operations;

/**
 * Filter for numeric values (players, time, rating, difficulty).
 */
public class NumberFilter extends Filter {
    
    public NumberFilter(GameData column, Operations operator, String value) {
        super(column, operator, value);
    }

    @Override
    public boolean apply(Game game) {
        double gameValue = switch (column) {
            case MIN_PLAYERS -> game.getMinPlayers();
            case MAX_PLAYERS -> game.getMaxPlayers();
            case MIN_TIME -> game.getMinPlayTime();
            case MAX_TIME -> game.getMaxPlayTime();
            case RATING -> game.getRating();
            case DIFFICULTY -> game.getDifficulty();
            default -> throw new IllegalArgumentException("Invalid column for number comparison: " + column);
        };

        double compareValue = Double.parseDouble(value);
        
        return switch (operator) {
            case EQUALS -> gameValue == compareValue;
            case GREATER_THAN -> gameValue > compareValue;
            case LESS_THAN -> gameValue < compareValue;
            case GREATER_THAN_EQUALS -> gameValue >= compareValue;
            case LESS_THAN_EQUALS -> gameValue <= compareValue;
        };
    }
} 