package student.sort;

import student.Game;
import student.GameData;
import java.util.Comparator;

public class NumericSortStrategy implements GameSortStrategy {
    /** The column to sort by. */
    private final GameData column;
    /** Whether to sort in ascending order. */
    private final boolean ascending;

    /**
     * Creates a new NumericSortStrategy.
     * @param column the column to sort by
     * @param ascending true for ascending order, false for descending
     */
    public NumericSortStrategy(GameData column, boolean ascending) {
        this.column = column;
        this.ascending = ascending;
    }

    @Override
    public Comparator<Game> getComparator() {
        Comparator<Game> comparator = switch (column) {
            case MIN_PLAYERS -> Comparator.comparingInt(Game::getMinPlayers);
            case MAX_PLAYERS -> Comparator.comparingInt(Game::getMaxPlayers);
            case MIN_TIME -> Comparator.comparingInt(Game::getMinPlayTime);
            case MAX_TIME -> Comparator.comparingInt(Game::getMaxPlayTime);
            case RATING -> Comparator.comparingDouble(Game::getRating);
            case DIFFICULTY -> Comparator.comparingDouble(Game::getDifficulty);
            default -> throw new IllegalArgumentException("Invalid column for numeric sort: " + column);
        };
        
        return ascending ? comparator : comparator.reversed();
    }
} 