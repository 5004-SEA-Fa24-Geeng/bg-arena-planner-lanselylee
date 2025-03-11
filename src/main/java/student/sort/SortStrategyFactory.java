package student.sort;

import student.GameData;

// Make the class final and add private constructor since it's a utility class
final class SortStrategyFactory {
    // Add private constructor to prevent instantiation
    private SortStrategyFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a sort strategy based on the sort string.
     * Format: "column:direction" (e.g., "name:asc", "rating:desc")
     *
     * @param sortStr the sort string
     * @return the appropriate sort strategy
     */
    public static GameSortStrategy createSortStrategy(String sortStr) {
        String[] parts = sortStr.toLowerCase().split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid sort format: " + sortStr);
        }

        String column = parts[0];
        boolean ascending = parts[1].equals("asc");

        GameData gameData = GameData.fromString(column);
        
        return switch (gameData) {
            case NAME -> new NameSortStrategy(ascending);
            default -> new NumericSortStrategy(gameData, ascending);
        };
    }
} 