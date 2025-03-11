package student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class GameList implements IGameList {
    /** List of board games stored in this collection. */
    private List<Game> games;
    /** Constant representing the "all" command for adding/removing games. */
    public static final String ADD_ALL = "all";

    /**
     * Constructs a new empty GameList.
     */
    public GameList() {
        this.games = new ArrayList<>();
    }

    /**
     * Adds a game to the list if it's a BoardGame and not already present.
     * @param game The game to add
     * @return true if the game was added successfully, false otherwise
     */
    public boolean addGame(Game game) {
        if (game == null || games.contains(game)) {
            return false;
        }
        return games.add(game);
    }

    /**
     * Removes a game from the list.
     * @param game The game to remove
     * @return true if the game was removed successfully, false otherwise
     */
    public boolean removeGame(Game game) {
        return games.remove(game);
    }

    /**
     * Checks if a game is present in the list.
     * @param game The game to check
     * @return true if the game is in the list, false otherwise
     */
    public boolean contains(Game game) {
        return games.contains(game);
    }

    /**
     * Returns the number of games in the list.
     * @return The size of the game list
     */
    public int size() {
        return games.size();
    }

    @Override
    public List<String> getGameNames() {
        List<String> names = new ArrayList<>();
        for (Game game : games) {
            names.add(game.getName());
        }
        Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
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
        try {
            // Create directory if it doesn't exist
            new File(filename).getParentFile().mkdirs();
            try (PrintWriter writer = new PrintWriter(filename)) {
                for (String name : getGameNames()) {
                    writer.println(name);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving file: " + filename + ": " + e.getMessage());
        }
    }

    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> filteredList = filtered.collect(Collectors.toList());
        
        if (filteredList.isEmpty()) {
            throw new IllegalArgumentException("No games to add");
        }

        if (str.equalsIgnoreCase(ADD_ALL)) {
            filteredList.forEach(game -> addGame((Game) game));
            return;
        }

        try {
            if (str.matches("\\d+")) {
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= filteredList.size()) {
                    throw new IllegalArgumentException("Invalid index");
                }
                addGame((Game) filteredList.get(index));
                return;
            }

            if (str.matches("\\d+-\\d+")) {
                String[] parts = str.split("-");
                int start = Integer.parseInt(parts[0]) - 1;
                int end = Integer.parseInt(parts[1]) - 1;
                if (start < 0 || end >= filteredList.size() || start > end) {
                    throw new IllegalArgumentException("Invalid range");
                }
                filteredList.subList(start, end + 1).forEach(game -> addGame((Game) game));
                return;
            }

            // Match by game name
            Optional<BoardGame> game = filteredList.stream()
                    .filter(g -> g.getName().equalsIgnoreCase(str))
                    .findFirst();
            if (game.isPresent()) {
                addGame((Game) game.get());
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
            if (index < 0 || index >= sortedGames.size()) {
                throw new IllegalArgumentException("Invalid index");
            }
            games.remove(sortedGames.get(index));
            return;
        }

        if (str.matches("\\d+-\\d+")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]) - 1;
            List<BoardGame> sortedGames = getSortedGames();
            if (start < 0 || end >= sortedGames.size() || start > end) {
                throw new IllegalArgumentException("Invalid range");
            }
            games.removeAll(sortedGames.subList(start, end + 1));
            return;
        }

        games.removeIf(g -> g.getName().equalsIgnoreCase(str));
    }

    private List<BoardGame> getSortedGames() {
        return games.stream()
                .sorted(Comparator.comparing(Game::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    /**
     * Gets game information in a formatted string.
     * @param name The name of the game to get information for
     * @return A formatted string containing game information, or null if game not found
     */
    public String getGameInfo(String name) {
        return games.stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(g -> String.format("%s (%d-%d players, %d minutes)", 
                    g.getName(), g.getMinPlayers(), g.getMaxPlayers(), g.getMinPlayTime()))
                .orElse(null);
    }

    /**
     * Gets a list of games matching the specified name.
     * @param name The name to search for
     * @return List of games matching the name
     */
    public List<Game> getGamesByName(String name) {
        List<Game> result = new ArrayList<>();
        for (Game game : games) {
            if (game.getName().equals(name)) {
                result.add(game);
            }
        }
        return result;
    }
}

