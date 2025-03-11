package student.filter;

import student.Game;
import student.GameData;
import student.Operations;

/**
 * Abstract class for filtering games.
 */
public abstract class Filter {
    protected final GameData column;
    protected final Operations operator;
    protected final String value;

    /**
     * Constructor for Filter.
     *
     * @param column   the column to filter on
     * @param operator the operator to use
     * @param value    the value to compare against
     */
    protected Filter(GameData column, Operations operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    /**
     * Apply the filter to a game.
     *
     * @param game the game to filter
     * @return true if the game passes the filter
     */
    public abstract boolean apply(Game game);
} 