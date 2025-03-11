package student.sort;

import student.Game;
import java.util.Comparator;

public class NameSortStrategy implements GameSortStrategy {
    /** Flag indicating whether to sort in ascending order. */
    private final boolean ascending;

    /**
     * Constructs a new NameSortStrategy.
     * @param ascending true for ascending order, false for descending order
     */
    public NameSortStrategy(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public Comparator<Game> getComparator() {
        Comparator<Game> comparator = Comparator.comparing(Game::getName, 
                String.CASE_INSENSITIVE_ORDER);
        return ascending ? comparator : comparator.reversed();
    }
} 
