import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JSONReader {
    /*
     * public static void main(String[] args) {
     * // Example usage:
     * JSONReader jsonReader = new JSONReader();
     * String json = jsonReader.readJsonFromFile("received_data.json");
     * 
     * Set<String> keys = jsonReader.getKeysFromJSON(json);
     * System.out.println("Keys with array values: " + keys);
     * 
     * for (String key : keys) {
     * List<Object> values = jsonReader.getArrayValues(json, key);
     * System.out.println("Array values for key '" + key + "': " + values);
     * }
     * }
     */

    public String readJsonFromFile(String filename) {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonBuilder.toString();
    }

    public Set<String> getKeysFromJSON(String json) {
        Set<String> keys = new HashSet<>();

        // Find keys with array values
        int start = json.indexOf('[');
        while (start != -1) {
            // Find the key preceding this array value
            int keyStart = json.lastIndexOf('"', start - 1);
            int keyEnd = json.lastIndexOf('"', keyStart - 1);
            String key = json.substring(keyEnd + 1, keyStart);
            keys.add(key);

            // Move to the next array value
            start = json.indexOf('[', start + 1);
        }

        return keys;
    }

    public List<Object> getArrayValues(String json, String key) {
        List<Object> values = new ArrayList<>();

        // Find the starting index of the array for the given key
        int startIndex = json.indexOf("\"" + key + "\":") + key.length() + 3;
        if (startIndex == -1) {
            return values;
        }

        // Find the ending index of the array for the given key
        int endIndex = json.indexOf("]", startIndex);
        if (endIndex == -1) {
            return values;
        }

        // Extract the array substring
        String arrayStr = json.substring(startIndex, endIndex);

        // Split the array string by commas to get individual values
        String[] valueArr = arrayStr.split(",");
        for (String value : valueArr) {
            // Remove leading and trailing spaces, and remove quotes if present
            value = value.trim().replaceAll("^\"|\"$", "");

            // Check if it's a number
            if (isNumeric(value)) {
                // Parse as double
                values.add(Double.parseDouble(value));
            } else {
                values.add(value); // Treat as string
            }
        }

        return values;
    }

    // Helper method to check if a string is numeric
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
