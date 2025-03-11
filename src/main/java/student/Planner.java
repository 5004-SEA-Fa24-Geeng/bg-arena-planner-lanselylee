package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.Comparator;

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
        // Default sorting comparator
        Comparator<BoardGame> comparator = (g1, g2) -> ascending 
            ? g1.getName().compareToIgnoreCase(g2.getName())
            : g2.getName().compareToIgnoreCase(g1.getName());

        // If no filter, return sorted stream
        if (filter == null || filter.trim().isEmpty()) {
            return filteredGames.stream().sorted(comparator);
        }

        System.out.println("Filtering with: " + filter);

        Stream<BoardGame> filtered = filteredGames.stream();
        
        if (filter.startsWith("minPlayers")) {
            filtered = handleMinPlayersFilter(filter, filtered);
        } else if (filter.startsWith("name")) {
            filtered = handleNameFilter(filter, filtered);
        }

        return filtered.sorted(comparator);
    }

    private Stream<BoardGame> handleNameFilter(String filter, Stream<BoardGame> stream) {
        String[] parts;
        String value;

        // Check longer operators first
        if (filter.contains(">=")) {
            parts = filter.split(">=");
            value = parts[1].trim().replaceAll("\"", "");
            return stream.filter(game -> game.getName().compareToIgnoreCase(value) >= 0);
        }
        if (filter.contains("<=")) {
            parts = filter.split("<=");
            value = parts[1].trim().replaceAll("\"", "");
            return stream.filter(game -> game.getName().compareToIgnoreCase(value) <= 0);
        }
        if (filter.contains("==")) {
            parts = filter.split("==");
            value = parts[1].trim().replaceAll("\"", "");
            return stream.filter(game -> game.getName().equalsIgnoreCase(value));
        }
        if (filter.contains("!=")) {
            parts = filter.split("!=");
            value = parts[1].trim().replaceAll("\"", "");
            return stream.filter(game -> !game.getName().equalsIgnoreCase(value));
        }
        if (filter.contains("~=")) {
            parts = filter.split("~=");
            value = parts[1].trim().replaceAll("\"", "");
            return stream.filter(game -> game.getName().toLowerCase().contains(value.toLowerCase()));
        }
        // Check single-character operators last
        if (filter.contains(">")) {
            parts = filter.split(">");
            value = parts[1].trim().replaceAll("\"", "");
            return stream.filter(game -> game.getName().compareToIgnoreCase(value) > 0);
        }
        if (filter.contains("<")) {
            parts = filter.split("<");
            value = parts[1].trim().replaceAll("\"", "");
            return stream.filter(game -> game.getName().compareToIgnoreCase(value) < 0);
        }
        return stream;
    }

    private Stream<BoardGame> handleMinPlayersFilter(String filter, Stream<BoardGame> stream) {
        String[] parts = filter.split("[><=]+");
        if (parts.length == 2) {
            int value = Integer.parseInt(parts[1].trim());
            if (filter.contains(">")) {
                return stream.filter(game -> game.getMinPlayers() > value);
            }
            // Add other numeric comparisons as needed
        }
        return stream;
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
