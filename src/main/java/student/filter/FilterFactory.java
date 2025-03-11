package student.filter;

import student.GameData;
import student.Operations;

/**
 * Factory for creating filters.
 */
public class FilterFactory {
    
    /**
     * Create a filter from a filter string.
     *
     * @param filterStr the filter string (e.g., "name=Catan" or "maxPlayers<=4")
     * @return the appropriate filter
     */
    public static Filter createFilter(String filterStr) {
        Operations operator = Operations.getOperatorFromStr(filterStr);
        String[] parts = filterStr.replaceAll(" ", "").split(operator.getOperator());
        
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid filter format: " + filterStr);
        }

        GameData column = GameData.fromString(parts[0]);
        String value = parts[1];

        return switch (column) {
            case NAME -> new StringFilter(column, operator, value);
            default -> new NumberFilter(column, operator, value);
        };
    }
} 