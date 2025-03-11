package student;

import student.filter.FilterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Planner implements IPlanner {
    /** List containing all board games available for filtering. */
    private final List<BoardGame> allGames;

    /**
     * Constructs a new Planner with the given set of board games.
     *
     * @param games the set of board games to be managed by this planner
     */
    public Planner(Set<BoardGame> games) {
        this.allGames = new ArrayList<>(games);
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        if (filter == null || filter.trim().isEmpty()) {
            return allGames.stream();
        }
        return allGames.stream()
                .filter(game -> FilterFactory.createFilter(filter).apply((Game) game));
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    @Override
    public void reset() {
        allGames.clear();
    }
}
