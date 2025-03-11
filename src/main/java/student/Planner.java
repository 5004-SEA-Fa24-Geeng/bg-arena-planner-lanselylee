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
                if (filterLower.matches("\\s*name\\s*==.*")) {
                    String value = filterStr.substring(filterStr.indexOf("==") + 2).trim();
                    return game.getName().equalsIgnoreCase(value);
                } else if (filterLower.matches("\\s*name\\s*~=.*")) {
                    String value = filterStr.substring(filterStr.indexOf("~=") + 2).trim();
                    return game.getName().toLowerCase().contains(value.toLowerCase());
                } else if (filterLower.matches("\\s*name\\s*>=.*")) {
                    String value = filterStr.substring(filterStr.indexOf(">=") + 2).trim();
                    return game.getName().compareToIgnoreCase(value) >= 0;
                } else if (filterLower.matches("\\s*name\\s*<=.*")) {
                    String value = filterStr.substring(filterStr.indexOf("<=") + 2).trim();
                    return game.getName().compareToIgnoreCase(value) <= 0;
                } else if (filterLower.matches("\\s*name\\s*>.*")) {
                    String value = filterStr.substring(filterStr.indexOf(">") + 1).trim();
                    return game.getName().compareToIgnoreCase(value) > 0;
                } else if (filterLower.matches("\\s*name\\s*<.*")) {
                    String value = filterStr.substring(filterStr.indexOf("<") + 1).trim();
                    return game.getName().compareToIgnoreCase(value) < 0;
                } else if (filterLower.matches("\\s*name\\s*!=.*")) {
                    String value = filterStr.substring(filterStr.indexOf("!=") + 2).trim();
                    return !game.getName().equalsIgnoreCase(value);
                } else if (filterLower.matches("\\s*(min_players|max_players|min_time|max_time|difficulty|rating|rank|year|id)\\s*(==|!=|>|<|>=|<=).*")) {
                    String field = filterLower.split("\\s*(==|!=|>=|<=|>|<)\\s*")[0];
                    String operator = filterLower.replaceAll(".*?(>=|<=|==|!=|>|<).*", "$1");
                    String valueStr = filterStr.substring(filterStr.indexOf(operator) + operator.length()).trim();

                    double numericValue = Double.parseDouble(valueStr);

                    double fieldValue = switch (field) {
                        case "min_players" -> game.getMinPlayers();
                        case "max_players" -> game.getMaxPlayers();
                        case "min_time" -> game.getMinPlayTime();
                        case "max_time" -> game.getMaxPlayTime();
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
                return true; // Default to keeping the game if no filter matches
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
