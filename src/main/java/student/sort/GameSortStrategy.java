package student.sort;

import student.Game;
import java.util.Comparator;

/**
 * Interface for game sorting strategies.
 */
public interface GameSortStrategy {
    /**
     * Get the comparator for sorting games.
     *
     * @return the comparator to use for sorting
     */
    Comparator<Game> getComparator();
} 