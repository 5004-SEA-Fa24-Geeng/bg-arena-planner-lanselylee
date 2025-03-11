package student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The Planner class is responsible for filtering and sorting board games based on user-defined criteria.
 * Implements the IPlanner interface.
 */
public class Planner implements IPlanner {
    /** List containing all board games available for filtering. */
    private final List<BoardGame> allGames;
    /** List that stores currently filtered games. */
    private List<BoardGame> filteredGames;

    /**
     * Constructs a new Planner with the given set of board games.
     *
     * @param games The set of board games to be managed by this planner.
     */
    public Planner(Set<BoardGame> games) {
        this.allGames = new ArrayList<>(games);
        this.filteredGames = new ArrayList<>(games);
    }

    /**
     * Filters the board games based on the provided filter string, using the default sorting method (by name).
     *
     * @param filter The filter condition in string format.
     * @return A stream of board games that match the given filter.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Filters the board games based on the given filter and sorting criteria.
     *
     * @param filter    The filter condition in string format.
     * @param sortOn    The column to sort the results on.
     * @param ascending Whether to sort in ascending order.
     * @return A stream of board games that match the given filter and sorting criteria.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        // Define sorting logic
        Comparator<BoardGame> comparator = Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            comparator = comparator.reversed();
        }

        // If no filter is applied, return all sorted games
        if (filter == null || filter.trim().isEmpty()) {
            return filteredGames.stream().sorted(comparator);
        }

        System.out.println("Filtering with: " + filter);

        Stream<BoardGame> filtered = filteredGames.stream();
        String lowerFilter = filter.toLowerCase();

        // Handle name~= (contains)
        if (lowerFilter.contains("name~=")) {
            String[] parts = lowerFilter.split("~=");
            if (parts.length == 2) {
                String value = parts[1].trim();
                filtered = filtered.filter(game -> game.getName().toLowerCase().contains(value));
            }
        }

        // Handle name== (exact match)
        if (lowerFilter.contains("name==")) {
            String[] parts = lowerFilter.split("==");
            if (parts.length == 2) {
                String value = parts[1].trim();
                filtered = filtered.filter(game -> game.getName().equalsIgnoreCase(value));
            }
        }

        // Handle name!= (not equal)
        if (lowerFilter.contains("name!=")) {
            String[] parts = lowerFilter.split("!=");
            if (parts.length == 2) {
                String value = parts[1].trim();
                filtered = filtered.filter(game -> !game.getName().equalsIgnoreCase(value));
            }
        }

        // Handle name> (greater than)
        if (lowerFilter.contains("name>")) {
            String[] parts = lowerFilter.split(">");
            if (parts.length == 2) {
                String value = parts[1].trim();
                filtered = filtered.filter(game -> game.getName().compareToIgnoreCase(value) > 0);
            }
        }

        // Handle name< (less than)
        if (lowerFilter.contains("name<")) {
            String[] parts = lowerFilter.split("<");
            if (parts.length == 2) {
                String value = parts[1].trim();
                filtered = filtered.filter(game -> game.getName().compareToIgnoreCase(value) < 0);
            }
        }

        return filtered.sorted(comparator);
    }

    /**
     * Filters the board games using the given filter and sorts them based on the specified column.
     *
     * @param filter The filter condition in string format.
     * @param sortOn The column to sort the results on.
     * @return A stream of board games that match the filter.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * Resets the filter by restoring the filtered list to the original game list.
     */
    @Override
    public void reset() {
        this.filteredGames = new ArrayList<>(allGames);
    }
}

