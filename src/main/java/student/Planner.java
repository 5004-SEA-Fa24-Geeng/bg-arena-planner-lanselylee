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

    /**
     * Constructs a new Planner with the given set of board games.
     *
     * @param games the set of board games to be managed by this planner
     */
    public Planner(Set<BoardGame> games) {
        this.allGames = new ArrayList<>(games);
    }

    /**
     * Filters the board games based on the provided filter string, using the default sorting method (by name).
     *
     * @param filter The filter condition in string format
     * @return A stream of board games that match the given filter
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Filters the board games based on the given filter and sorting criteria.
     *
     * @param filter    The filter condition in string format
     * @param sortOn    The column to sort the results on
     * @param ascending Whether to sort in ascending order
     * @return A stream of board games that match the given filter and sorting criteria
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        if (filter == null || filter.trim().isEmpty()) {
            return allGames.stream()
                    .sorted((g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName()));
        }

        System.out.println("Filtering with: " + filter);

        // Check for name equals operation (e.g., "name==Go")
        if (filter.contains("==")) {
            String[] parts = filter.split("==");
            if (parts.length == 2) {
                String columnName = parts[0].trim();
                String value = parts[1].trim().replaceAll("\"", "");

                if (columnName.equalsIgnoreCase("name")) {
                    return allGames.stream()
                            .filter(game -> game.getName().equalsIgnoreCase(value))
                            .sorted((g1, g2) -> ascending 
                                ? g1.getName().compareToIgnoreCase(g2.getName())
                                : g2.getName().compareToIgnoreCase(g1.getName()));
                }
            }
        }

        // Check for name contains operation (e.g., "name~=Go")
        if (filter.contains("~=")) {
            String[] parts = filter.split("~=");
            if (parts.length == 2) {
                String columnName = parts[0].trim();
                String value = parts[1].trim().replaceAll("\"", "");

                if (columnName.equalsIgnoreCase("name")) {
                    return allGames.stream()
                            .filter(game -> game.getName().toLowerCase().contains(value.toLowerCase()))
                            .sorted((g1, g2) -> ascending 
                                ? g1.getName().compareToIgnoreCase(g2.getName())
                                : g2.getName().compareToIgnoreCase(g1.getName()));
                }
            }
        }
        
        // Check for less than operation (e.g., "name<Go")
        if (filter.contains("<")) {
            String[] parts = filter.split("<");
            if (parts.length == 2) {
                String columnName = parts[0].trim();
                String value = parts[1].trim().replaceAll("\"", "");

                if (columnName.equalsIgnoreCase("name")) {
                    return allGames.stream()
                            .filter(game -> game.getName().compareToIgnoreCase(value) < 0)
                            .sorted((g1, g2) -> ascending 
                                ? g1.getName().compareToIgnoreCase(g2.getName())
                                : g2.getName().compareToIgnoreCase(g1.getName()));
                }
            }
        }

        // Check for greater than operation (e.g., "name>Go")
        if (filter.contains(">")) {
            String[] parts = filter.split(">");
            if (parts.length == 2) {
                String columnName = parts[0].trim();
                String value = parts[1].trim().replaceAll("\"", "");

                if (columnName.equalsIgnoreCase("name")) {
                    return allGames.stream()
                            .filter(game -> game.getName().compareToIgnoreCase(value) > 0)
                            .sorted((g1, g2) -> ascending 
                                ? g1.getName().compareToIgnoreCase(g2.getName())
                                : g2.getName().compareToIgnoreCase(g1.getName()));
                }
            }
        }

        // Default case: treat the filter as a simple name contains search
        return allGames.stream()
                .filter(game -> game.getName().toLowerCase().contains(filter.toLowerCase()))
                .sorted((g1, g2) -> ascending 
                    ? g1.getName().compareToIgnoreCase(g2.getName())
                    : g2.getName().compareToIgnoreCase(g1.getName()));
    }

    /**
     * Filters the board games using the given filter and sorts them based on the specified column.
     *
     * @param filter The filter condition in string format
     * @param sortOn The column to sort the results on
     * @return A stream of board games that match the filter
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * Resets the filter by clearing all applied filters and restoring the original game list.
     */
    @Override
    public void reset() {
        allGames.clear();
    }
}

