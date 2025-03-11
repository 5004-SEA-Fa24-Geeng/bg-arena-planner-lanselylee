package student.filter;

import student.Game;
import student.GameData;
import student.Operations;

/**
 * Filter for numeric values (players, time, rating, difficulty).
 */
public class NumberFilter extends Filter {
    
    /**
     * Constructs a new NumberFilter.
     *
     * @param column The game data column to filter on
     * @param operator The operation to apply in the filter
     * @param value The value to compare against
     */
    public NumberFilter(GameData column, Operations operator, String value) {
        super(column, operator, value);
    }

    /**
     * Applies the filter to a game based on numeric values.
     *
     * @param game The game to filter
     * @return true if the game matches the filter criteria, false otherwise
     */
    @Override
    public boolean apply(Game game) {
        double gameValue = switch (getColumn()) {
            case MIN_PLAYERS -> game.getMinPlayers();
            case MAX_PLAYERS -> game.getMaxPlayers();
            case MIN_TIME -> game.getMinPlayTime();
            case MAX_TIME -> game.getMaxPlayTime();
            case RATING -> game.getRating();
            case DIFFICULTY -> game.getDifficulty();
            default -> throw new IllegalArgumentException("Invalid column for number comparison: " + getColumn());
        };

        double compareValue = Double.parseDouble(getValue());
        
        return switch (getOperator()) {
            case EQUALS -> gameValue == compareValue;
            case GREATER_THAN -> gameValue > compareValue;
            case LESS_THAN -> gameValue < compareValue;
            case GREATER_THAN_EQUALS -> gameValue >= compareValue;
            case LESS_THAN_EQUALS -> gameValue <= compareValue;
        };
    }
} 
