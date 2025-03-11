package student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Planner implements IPlanner {
    private final List<BoardGame> allGames;

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

            filteredList = filteredList.stream().filter(game -> {
                // Extract operator and value first
                String operator = null;
                String value = null;
                
                if (filterLower.contains("==")) {
                    operator = "==";
                    value = filterStr.substring(filterStr.indexOf("==") + 2).trim();
                } else if (filterLower.contains("~=")) {
                    operator = "~=";
                    value = filterStr.substring(filterStr.indexOf("~=") + 2).trim();
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
                }

                if (filterLower.startsWith("name") && operator != null) {
                    String gameName = game.getName();
                    return switch (operator) {
                        case "==" -> gameName.equalsIgnoreCase(value);
                        case "~=" -> gameName.toLowerCase().contains(value.toLowerCase());
                        case ">=" -> gameName.compareToIgnoreCase(value) >= 0;
                        case ">" -> gameName.compareToIgnoreCase(value) > 0;
                        case "<=" -> gameName.compareToIgnoreCase(value) <= 0;
                        case "<" -> gameName.compareToIgnoreCase(value) < 0;
                        case "!=" -> !gameName.equalsIgnoreCase(value);
                        default -> false;
                    };
                }
                return false;  // If no filter matches, exclude the game
            }).toList();
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
