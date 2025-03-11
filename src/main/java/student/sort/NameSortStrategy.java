package student.sort;

import student.Game;
import java.util.Comparator;

public class NameSortStrategy implements GameSortStrategy {
    private final boolean ascending;

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