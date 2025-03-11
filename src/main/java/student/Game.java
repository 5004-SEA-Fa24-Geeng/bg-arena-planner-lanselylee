package student;

public class Game extends BoardGame {
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
        super(name, 0, minPlayers, maxPlayers, playTime, playTime, difficulty, 0, rating, 2024);
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minPlayTime = playTime;
        this.maxPlayTime = playTime;
        this.rating = rating;
        this.difficulty = difficulty;
    }

    // Getters
    public String getName() {
        return name;
    }
    
    public int getMinPlayers() {
        return minPlayers;
    }
    
    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    public int getMinPlayTime() {
        return minPlayTime;
    }
    
    public int getMaxPlayTime() {
        return maxPlayTime;
    }
    
    public double getRating() {
        return rating;
    }
    
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