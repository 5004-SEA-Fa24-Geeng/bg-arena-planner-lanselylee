package student;

public class Game {
    /** The name of the game. */
    private final String name;
    /** The minimum number of players required. */
    private final int minPlayers;
    /** The maximum number of players allowed. */
    private final int maxPlayers;
    /** The minimum play time in minutes. */
    private final int minPlayTime;
    /** The maximum play time in minutes. */
    private final int maxPlayTime;
    /** The rating of the game. */
    private final double rating;
    /** The difficulty level of the game. */
    private final double difficulty;

    /**
     * Constructs a new Game instance.
     * @param name The name of the game
     * @param minPlayers Minimum number of players
     * @param maxPlayers Maximum number of players
     * @param playTime Play time in minutes
     * @param rating Game rating
     * @param difficulty Game difficulty level
     */
    public Game(String name, int minPlayers, int maxPlayers, int playTime, double rating, double difficulty) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minPlayTime = playTime;
        this.maxPlayTime = playTime;
        this.rating = rating;
        this.difficulty = difficulty;
    }

    // Getters
    /**
     * Gets the name of the game.
     * @return the game name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the minimum number of players required.
     * @return the minimum number of players
     */
    public int getMinPlayers() {
        return minPlayers;
    }
    
    /**
     * Gets the maximum number of players allowed.
     * @return the maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    /**
     * Gets the minimum play time in minutes.
     * @return the minimum play time
     */
    public int getMinPlayTime() {
        return minPlayTime;
    }
    
    /**
     * Gets the maximum play time in minutes.
     * @return the maximum play time
     */
    public int getMaxPlayTime() {
        return maxPlayTime;
    }
    
    /**
     * Gets the rating of the game.
     * @return the game rating
     */
    public double getRating() {
        return rating;
    }
    
    /**
     * Gets the difficulty level of the game.
     * @return the game difficulty
     */
    public double getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game game)) {
            return false;
        }
        return name.equalsIgnoreCase(game.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
} 

