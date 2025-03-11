package student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GameList implements IGameList {
    private List<BoardGame> games;
    public static final String ADD_ALL = "all";

    public GameList() {
        this.games = new ArrayList<>();
    }

    public boolean addGame(Game game) {
        if (!(game instanceof BoardGame)) {
            return false;
        }
        BoardGame boardGame = (BoardGame) game;
        return !games.contains(boardGame) && games.add(boardGame);
    }

    public boolean removeGame(Game game) {
        return games.remove(game);
    }

    public boolean contains(Game game) {
        if (!(game instanceof BoardGame)) {
            return false;
        }
        BoardGame boardGame = (BoardGame) game;
        return games.contains(boardGame);
    }

    public int size() {
        return games.size();
    }

    @Override
    public List<String> getGameNames() {
        return games.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        games.clear();
    }

    @Override
    public int count() {
        return games.size();
    }

    @Override
    public void saveGame(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (String name : getGameNames()) {
                writer.println(name);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving file: " + filename);
        }
    }

    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered.collect(Collectors.toList());
        
        if (filteredList.isEmpty()) {
            throw new IllegalArgumentException("No games to add");
        }

        if (str.equalsIgnoreCase(ADD_ALL)) {
            filteredList.forEach(game -> addGame((Game)game));
            return;
        }

        try {
            if (str.matches("\\d+")) {
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= filteredList.size()) {
                    throw new IllegalArgumentException("Invalid index");
                }
                addGame((Game)filteredList.get(index));
                return;
            }

            if (str.matches("\\d+-\\d+")) {
                String[] parts = str.split("-");
                int start = Integer.parseInt(parts[0]) - 1;
                int end = Integer.parseInt(parts[1]) - 1;
                if (start < 0 || end >= filteredList.size() || start > end) {
                    throw new IllegalArgumentException("Invalid range");
                }
                filteredList.subList(start, end + 1).forEach(game -> addGame((Game)game));
                return;
            }

            // Match by game name
            Optional<BoardGame> game = filteredList.stream()
                    .filter(g -> g.getName().equalsIgnoreCase(str))
                    .findFirst();
            if (game.isPresent()) {
                addGame((Game)game.get());
            } else {
                throw new IllegalArgumentException("Game not found");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format");
        }
    }

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str.equalsIgnoreCase(ADD_ALL)) {
            clear();
            return;
        }

        if (str.matches("\\d+")) {
            int index = Integer.parseInt(str) - 1;
            List<BoardGame> sortedGames = getSortedGames();
            if (index < 0 || index >= sortedGames.size()) throw new IllegalArgumentException("Invalid index");
            games.remove(sortedGames.get(index));
            return;
        }

        if (str.matches("\\d+-\\d+")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]) - 1;
            List<BoardGame> sortedGames = getSortedGames();
            if (start < 0 || end >= sortedGames.size() || start > end) throw new IllegalArgumentException("Invalid range");
            games.removeAll(sortedGames.subList(start, end + 1));
            return;
        }

        games.removeIf(g -> g.getName().equalsIgnoreCase(str));
    }

    private List<BoardGame> getSortedGames() {
        return games.stream()
                .sorted(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public String getGameInfo(String name) {
        return games.stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(g -> String.format("%s (%d-%d players, %d minutes)", 
                    g.getName(), g.getMinPlayers(), g.getMaxPlayers(), g.getMinPlayTime()))
                .orElse(null);
    }

    public List<Game> getGamesByName(String name) {
        return games.stream()
            .filter(game -> game.getName().equals(name))
            .map(game -> (Game) game)
            .collect(Collectors.toList());
    }
}
