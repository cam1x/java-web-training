package by.training.parser;

import java.util.HashMap;
import java.util.Map;

public class LineParser {

    private final static String PARAMETER_SEPARATOR = ",";
    private final static String KEY_VALUE_SEPARATOR = ":";

    public Map<String,String> parse(String line) {
        Map<String,String> parsed = new HashMap<>();
        String[] parameters = line.split(PARAMETER_SEPARATOR);

        for (String part : parameters){
            String[] typeValue = part.split(KEY_VALUE_SEPARATOR);
            if (typeValue.length == 2) {
                parsed.put(typeValue[0].trim().toLowerCase(), typeValue[1].trim().toLowerCase());
            }
        }

        return parsed;
    }
}
