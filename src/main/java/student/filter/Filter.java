package student.filter;

import student.Game;
import student.GameData;
import student.Operations;

/**
 * Abstract class for filtering games.
 */
public abstract class Filter {
    /** The column to filter on. */
    private final GameData column;
    /** The operator to use for comparison. */
    private final Operations operator;
    /** The value to compare against. */
    private final String value;

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
     * Gets the column being filtered on.
     *
     * @return the column
     */
    protected GameData getColumn() {
        return column;
    }

    /**
     * Gets the operator being used.
     *
     * @return the operator
     */
    protected Operations getOperator() {
        return operator;
    }

    /**
     * Gets the value being compared against.
     *
     * @return the value
     */
    protected String getValue() {
        return value;
    }

    /**
     * Apply the filter to a game.
     *
     * @param game the game to filter
     * @return true if the game passes the filter
     */
    public abstract boolean apply(Game game);
}
