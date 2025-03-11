package student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Planner implements IPlanner {
    /** List containing all board games available for planning. */
    private final List<BoardGame> allGames;

    /**
     * Constructs a new Planner with the given set of board games.
     *
     * @param games Set of board games to be managed by the planner
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

        if (filter == null || filter.trim().isEmpty()) {
            return allGames.stream().sorted(comparator);
        }

        String[] filters = filter.split(",");
        List<BoardGame> filteredList = new ArrayList<>(allGames);

        for (String f : filters) {
            String filterStr = f.trim();
            String filterLower = filterStr.toLowerCase();
            System.out.println("Processing filter: " + filterStr);

            filteredList = filteredList.stream().filter(game -> {
                if (filterLower.startsWith("name")) {
                    String gameName = game.getName();
                    
                    // Handle name operators in order of length (longest first)
                    if (filterLower.contains(">=")) {
                        String value = filterStr.substring(filterStr.indexOf(">=") + 2).trim();
                        return gameName.compareToIgnoreCase(value) >= 0;
                    } else if (filterLower.contains("<=")) {
                        String value = filterStr.substring(filterStr.indexOf("<=") + 2).trim();
                        return gameName.compareToIgnoreCase(value) <= 0;
                    } else if (filterLower.contains("==")) {
                        String value = filterStr.substring(filterStr.indexOf("==") + 2).trim();
                        return gameName.equalsIgnoreCase(value);
                    } else if (filterLower.contains("!=")) {
                        String value = filterStr.substring(filterStr.indexOf("!=") + 2).trim();
                        return !gameName.equalsIgnoreCase(value);
                    } else if (filterLower.contains("~=")) {
                        String value = filterStr.substring(filterStr.indexOf("~=") + 2).trim();
                        return gameName.toLowerCase().contains(value.toLowerCase());
                    } else if (filterLower.contains(">")) {
                        String value = filterStr.substring(filterStr.indexOf(">") + 1).trim();
                        return gameName.compareToIgnoreCase(value) > 0;
                    } else if (filterLower.contains("<")) {
                        String value = filterStr.substring(filterStr.indexOf("<") + 1).trim();
                        return gameName.compareToIgnoreCase(value) < 0;
                    }
                }

                // Handle numeric filters
                if (filterLower.replaceAll("[^a-z]", "").toLowerCase().matches(".*(minplayers|maxplayers|mintime|"
                        + "minplaytime|maxtime|maxplaytime|difficulty|rating|rank|year|id).*")) {
                    // Extract the field name before the operator
                    String field = filterLower.split("[<>=!~]+")[0].replaceAll("[^a-z]", "").toLowerCase();
                    String operator;
                    String value;
                    
                    if (filterLower.contains("==")) {
                        operator = "==";
                        value = filterStr.substring(filterStr.indexOf("==") + 2).trim();
                    } else if (filterLower.contains(">=")) {
                        operator = ">=";
                        value = filterStr.substring(filterStr.indexOf(">=") + 2).trim();
                    } else if (filterLower.contains(">")) {
                        operator = ">";
                        value = filterStr.substring(filterStr.indexOf(">") + 1).trim();
                    } else if (filterLower.contains("<=")) {
                        operator = "<=";
                        value = filterStr.substring(filterStr.indexOf("<=") + 2).trim();
                    } else if (filterLower.contains("<")) {
                        operator = "<";
                        value = filterStr.substring(filterStr.indexOf("<") + 1).trim();
                    } else if (filterLower.contains("!=")) {
                        operator = "!=";
                        value = filterStr.substring(filterStr.indexOf("!=") + 2).trim();
                    } else {
                        return false;
                    }

                    double numericValue = Double.parseDouble(value);
                    double fieldValue = switch (field) {
                        case "minplayers" -> game.getMinPlayers();
                        case "maxplayers" -> game.getMaxPlayers();
                        case "mintime", "minplaytime" -> game.getMinPlayTime();
                        case "maxtime", "maxplaytime" -> game.getMaxPlayTime();
                        case "difficulty" -> game.getDifficulty();
                        case "rating" -> game.getRating();
                        case "rank" -> game.getRank();
                        case "year" -> game.getYearPublished();
                        case "id" -> game.getId();
                        default -> 0;
                    };

                    return switch (operator) {
                        case ">" -> fieldValue > numericValue;
                        case "<" -> fieldValue < numericValue;
                        case ">=" -> fieldValue >= numericValue;
                        case "<=" -> fieldValue <= numericValue;
                        case "==" -> fieldValue == numericValue;
                        case "!=" -> fieldValue != numericValue;
                        default -> false;
                    };
                }

                return false;  // If we don't recognize the filter type, exclude the game
            }).toList();
            System.out.println("Filtered list: " + filteredList.stream()
                .map(BoardGame::getName)
                .collect(Collectors.joining(", ")));
        }

        return filteredList.stream().sorted(comparator);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    @Override
    public void reset() {
        // Reset operation is not provided in the original code or the new implementation
        // It's assumed to exist as it's called in the reset() method of the original code
    }
}
