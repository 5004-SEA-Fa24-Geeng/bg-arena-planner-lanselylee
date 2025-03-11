package student;

import java.util.ArrayList;
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
        if (filter == null || filter.trim().isEmpty()) {
            return filteredGames.stream()
                    .sorted((g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName()));
        }

        System.out.println("Filtering with: " + filter);

        // Handle numeric comparisons for minPlayers
        if (filter.startsWith("minPlayers")) {
            String[] parts = filter.split("[><=]+");
            if (parts.length == 2) {
                int value = Integer.parseInt(parts[1].trim());
                if (filter.contains(">")) {
                    return filteredGames.stream()
                            .filter(game -> game.getMinPlayers() > value)
                            .sorted((g1, g2) -> ascending
                                    ? g1.getName().compareToIgnoreCase(g2.getName())
                                    : g2.getName().compareToIgnoreCase(g1.getName()));
                }
                // Add other numeric comparisons as needed
            }
        }

        // Handle name contains (name~=value)
        if (filter.contains("~=")) {
            return applyStringFilter(filter, "~=", (gameName, value) ->
                    gameName.toLowerCase().contains(value.toLowerCase()), ascending);
        }

        // Handle name equals (name==value)
        if (filter.contains("==")) {
            return applyStringFilter(filter, "==", String::equalsIgnoreCase, ascending);
        }

        // Handle name not equals (name!=value)
        if (filter.contains("!=")) {
            return applyStringFilter(filter, "!=", (gameName, value) ->
                    !gameName.equalsIgnoreCase(value), ascending);
        }

        // Handle name greater than (name>value)
        if (filter.contains(">") && !filter.contains(">=")) {
            return applyStringFilter(filter, ">", (gameName, value) ->
                    gameName.compareToIgnoreCase(value) > 0, ascending);
        }

        // Handle name greater than or equal (name>=value)
        if (filter.contains(">=")) {
            return applyStringFilter(filter, ">=", (gameName, value) ->
                    gameName.compareToIgnoreCase(value) >= 0, ascending);
        }

        // Handle name less than (name<value)
        if (filter.contains("<") && !filter.contains("<=")) {
            return applyStringFilter(filter, "<", (gameName, value) ->
                    gameName.compareToIgnoreCase(value) < 0, ascending);
        }

        // Handle name less than or equal (name<=value)
        if (filter.contains("<=")) {
            return applyStringFilter(filter, "<=", (gameName, value) ->
                    gameName.compareToIgnoreCase(value) <= 0, ascending);
        }

        // Default: Treat filter as name contains
        return filteredGames.stream()
                .filter(game -> game.getName().toLowerCase().contains(filter.toLowerCase()))
                .sorted((g1, g2) -> ascending
                        ? g1.getName().compareToIgnoreCase(g2.getName())
                        : g2.getName().compareToIgnoreCase(g1.getName()));
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

    /**
     * Helper method to apply string-based filtering based on a given operator.
     *
     * @param filter The filter string.
     * @param operator The operator used in the filter (e.g., "==", "~=", ">", "<").
     * @param comparisonFunction A lambda function defining the comparison condition.
     * @param ascending Whether to sort in ascending order.
     * @return A stream of filtered and sorted board games.
     */
    private Stream<BoardGame> applyStringFilter(String filter, String operator,
                                                StringComparator comparisonFunction, boolean ascending) {
        String[] parts = filter.split(operator);
        if (parts.length == 2) {
            String columnName = parts[0].trim();
            String value = parts[1].trim().replaceAll("\"", "");

            if (columnName.equalsIgnoreCase("name")) {
                return filteredGames.stream()
                        .filter(game -> comparisonFunction.compare(game.getName(), value))
                        .sorted((g1, g2) -> ascending
                                ? g1.getName().compareToIgnoreCase(g2.getName())
                                : g2.getName().compareToIgnoreCase(g1.getName()));
            }
        }
        return Stream.empty();
    }

    /**
     * Functional interface for comparing two strings.
     */
    @FunctionalInterface
    private interface StringComparator {
        boolean compare(String gameName, String value);
    }
}
