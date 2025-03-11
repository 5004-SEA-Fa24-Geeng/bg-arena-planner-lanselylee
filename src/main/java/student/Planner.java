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
        // Define comparator based on sortOn parameter and ascending flag
        Comparator<BoardGame> comparator;
        
        if (sortOn == GameData.NAME) {
            comparator = (g1, g2) -> ascending 
                ? g1.getName().compareToIgnoreCase(g2.getName())
                : g2.getName().compareToIgnoreCase(g1.getName());
        } else {
            // Default to name sorting if sortOn is not handled
            comparator = (g1, g2) -> ascending 
                ? g1.getName().compareToIgnoreCase(g2.getName())
                : g2.getName().compareToIgnoreCase(g1.getName());
        }

        // If no filter, return sorted stream
        if (filter == null || filter.trim().isEmpty()) {
            return filteredGames.stream().sorted(comparator);
        }

        System.out.println("Filtering with: " + filter);

        // Apply appropriate filter based on the filter string
        Stream<BoardGame> filtered;
        
        String normalizedFilter = filter.toLowerCase().trim().replace("_", "");
        if (normalizedFilter.startsWith("minplayers")) {
            filtered = applyMinPlayersFilter(filter);
        } else {
            // Always treat as a name filter, either with explicit "name" prefix or without
            filtered = applyNameFilter(normalizedFilter.startsWith("name") ? filter : "name~=" + filter);
        }

        return filtered.sorted(comparator);
    }

    /**
     * Applies name-based filters based on the given filter string.
     *
     * @param filter The filter condition in string format.
     * @return Stream of board games that match the filter.
     */
    private Stream<BoardGame> applyNameFilter(String filter) {
        // Extract the value part and remove quotes, convert to lowercase for case-insensitive comparison
        String[] parts = filter.split("[><=!~]+", 2);
        final String value = parts.length > 1 ? 
            parts[1].trim().replaceAll("\"", "").toLowerCase() : "";
        
        if (filter.contains(">=")) {
            return filteredGames.stream()
                .filter(game -> game.getName().compareTo(value) >= 0);
        } else if (filter.contains("<=")) {
            return filteredGames.stream()
                .filter(game -> game.getName().compareTo(value) <= 0);
        } else if (filter.contains("==")) {
            return filteredGames.stream()
                .filter(game -> game.getName().equalsIgnoreCase(value));
        } else if (filter.contains("!=")) {
            return filteredGames.stream()
                .filter(game -> !game.getName().equalsIgnoreCase(value));
        } else if (filter.contains("~=")) {
            // Match only if the value appears as a word or part of a word
            String regex = ".*\\b" + value + ".*";
            return filteredGames.stream()
                .filter(game -> game.getName().toLowerCase().matches(regex));
        } else if (filter.contains(">")) {
            return filteredGames.stream()
                .filter(game -> game.getName().compareTo(value) > 0);
        } else if (filter.contains("<")) {
            return filteredGames.stream()
                .filter(game -> game.getName().compareTo(value) < 0);
        }
        
        // If no operator is matched, return unfiltered stream
        return filteredGames.stream();
    }

    /**
     * Applies minimum player filter based on the given filter string.
     *
     * @param filter The filter condition in string format.
     * @return Stream of board games that match the filter.
     */
    private Stream<BoardGame> applyMinPlayersFilter(String filter) {
        if (filter.contains(">")) {
            try {
                int value = Integer.parseInt(filter.split(">")[1].trim());
                return filteredGames.stream()
                    .filter(game -> game.getMinPlayers() > value);
            } catch (NumberFormatException e) {
                // In case of parsing error, return unfiltered
                return filteredGames.stream();
            }
        }
        
        // If no operator is matched, return unfiltered stream
        return filteredGames.stream();
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