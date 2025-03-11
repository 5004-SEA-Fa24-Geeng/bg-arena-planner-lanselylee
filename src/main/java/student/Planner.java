package student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Planner implements IPlanner {
    private final List<BoardGame> allGames;
    private List<BoardGame> filteredGames;

    public Planner(Set<BoardGame> games) {
        this.allGames = new ArrayList<>(games);
        this.filteredGames = new ArrayList<>(games);
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
        Stream<BoardGame> filtered = allGames.stream();

        for (String f : filters) {
            String filterStr = f.trim();
            String filterLower = filterStr.toLowerCase();

            filtered = filtered.filter(game -> {
                String currentGameName = game.getName();

                if (filterLower.matches("\\s*name\\s*==.*")) {
                    String value = filterStr.substring(filterStr.indexOf('=') + 2).trim();
                    return currentGameName.equalsIgnoreCase(value);
                } else if (filterLower.matches("\\s*name\\s*~=.*")) {
                    String value = filterStr.substring(filterStr.indexOf('=') + 2).trim().toLowerCase();
                    return currentGameName.toLowerCase().contains(value);
                } else if (filterLower.matches("\\s*name\\s*>=.*")) {
                    String value = filterStr.substring(filterStr.indexOf('=') + 1).trim();
                    return currentGameName.compareToIgnoreCase(value) >= 0;
                } else if (filterLower.matches("\\s*name\\s*<=.*")) {
                    String value = filterStr.substring(filterStr.indexOf('=') + 1).trim();
                    return currentGameName.compareToIgnoreCase(value) <= 0;
                } else if (filterLower.matches("\\s*name\\s*>.*")) {
                    String value = filterStr.substring(filterStr.indexOf('>') + 1).trim();
                    return currentGameName.compareToIgnoreCase(value) > 0;
                } else if (filterLower.matches("\\s*name\\s*<.*")) {
                    String value = filterStr.substring(filterStr.indexOf('<') + 1).trim();
                    return currentGameName.compareToIgnoreCase(value) < 0;
                } else if (filterLower.matches("\\s*name\\s*!=.*")) {
                    String value = filterStr.substring(filterStr.indexOf('=') + 1).trim();
                    return !currentGameName.equalsIgnoreCase(value);
                }
                return false;
            });
        }

        return filtered.sorted(comparator);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    @Override
    public void reset() {
        this.filteredGames = new ArrayList<>(allGames);
    }
}
