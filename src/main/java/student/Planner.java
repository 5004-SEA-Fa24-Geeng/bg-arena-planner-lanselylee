package student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

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
        System.out.println("Planner initialized with " + allGames.size() + " games.");
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
        // Add debug logging at the start
        System.out.println("DEBUG - All games in planner:");
        allGames.forEach(game -> System.out.println("Game: " + game.getName()));
        
        System.out.println("Filtering games with filter: '" + filter + "', sorting by: " + sortOn + ", ascending: " + ascending);
        System.out.println("Total games before filtering: " + filteredGames.size());

        // Define sorting logic based on sortOn parameter
        Comparator<BoardGame> comparator = switch (sortOn) {
            case NAME -> Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
            case RATING -> Comparator.comparing(BoardGame::getRating);
            case DIFFICULTY -> Comparator.comparing(BoardGame::getDifficulty);
            case MIN_TIME -> Comparator.comparing(BoardGame::getMinPlayTime);
            case MIN_PLAYERS -> Comparator.comparing(BoardGame::getMinPlayers);
            case MAX_PLAYERS -> Comparator.comparing(BoardGame::getMaxPlayers);
            case MAX_TIME -> Comparator.comparing(BoardGame::getMaxPlayTime);
            case RANK -> Comparator.comparing(BoardGame::getRank);
            case YEAR -> Comparator.comparing(BoardGame::getYearPublished);
            case ID -> Comparator.comparing(BoardGame::getId);
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }

        // If no filter is applied, return all sorted games
        if (filter == null || filter.trim().isEmpty()) {
            System.out.println("No filter provided, returning all games sorted.");
            return filteredGames.stream().sorted(comparator);
        }

        String[] filters = filter.split(",");
        Stream<BoardGame> filtered = filteredGames.stream();
        
        for (String f : filters) {
            String filterStr = f.trim();
            System.out.println("Processing filter: " + filterStr);

            filtered = filtered.filter(game -> {
                String gameName = game.getName().toLowerCase();
                
                // Handle exact match (name==)
                if (filterStr.toLowerCase().startsWith("name ==") || filterStr.toLowerCase().startsWith("name==")) {
                    String value = filterStr.toLowerCase().replace("name==", "").replace("name ==", "").trim();
                    return gameName.equals(value);
                }
                // Handle substring match (name~=)
                else if (filterStr.toLowerCase().startsWith("name ~=") || filterStr.toLowerCase().startsWith("name~=")) {
                    String value = filterStr.toLowerCase().replace("name~=", "").replace("name ~=", "").trim();
                    return gameName.contains(value);
                }
                // Handle greater than or equal (name>=)
                else if (filterStr.toLowerCase().startsWith("name >=") || filterStr.toLowerCase().startsWith("name>=")) {
                    String value = filterStr.toLowerCase().replace("name>=", "").replace("name >=", "").trim();
                    return gameName.compareToIgnoreCase(value) >= 0;
                }
                // Handle greater than (name>)
                else if (filterStr.toLowerCase().startsWith("name >") || filterStr.toLowerCase().startsWith("name>")) {
                    String value = filterStr.toLowerCase().replace("name>", "").replace("name >", "").trim();
                    return gameName.compareToIgnoreCase(value) > 0;
                }
                // Handle less than or equal (name<=)
                else if (filterStr.toLowerCase().startsWith("name <=") || filterStr.toLowerCase().startsWith("name<=")) {
                    String value = filterStr.toLowerCase().replace("name<=", "").replace("name <=", "").trim();
                    return gameName.compareToIgnoreCase(value) <= 0;
                }
                // Handle less than (name<)
                else if (filterStr.toLowerCase().startsWith("name <") || filterStr.toLowerCase().startsWith("name<")) {
                    String value = filterStr.toLowerCase().replace("name<", "").replace("name <", "").trim();
                    return gameName.compareToIgnoreCase(value) < 0;
                }
                return false; // If no filter matches, exclude the game
            });
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
        System.out.println("Resetting filteredGames to allGames. Total games: " + allGames.size());
        this.filteredGames = new ArrayList<>(allGames);
    }
}

