package student;

public class Game extends BoardGame {
    private final String name;
    private final int minPlayers;
    private final int maxPlayers;
    private final int minPlayTime;
    private final int maxPlayTime;
    private final double rating;
    private final double difficulty;

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
    public String getName() { return name; }
    public int getMinPlayers() { return minPlayers; }
    public int getMaxPlayers() { return maxPlayers; }
    public int getMinPlayTime() { return minPlayTime; }
    public int getMaxPlayTime() { return maxPlayTime; }
    public double getRating() { return rating; }
    public double getDifficulty() { return difficulty; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game game)) return false;
        return name.equalsIgnoreCase(game.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
} 