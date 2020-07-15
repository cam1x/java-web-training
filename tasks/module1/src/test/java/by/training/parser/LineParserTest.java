package by.training.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class LineParserTest {

    @Test
    public void shouldParseValidLine() {
        String line ="field1: value1, field2: value2";
        Map<String, String> expected = new HashMap<>();
        expected.put("field1","value1");
        expected.put("field2","value2");
        Map<String, String> parsed = new LineParser().parse(line);
        assertTrue(expected.keySet().equals(parsed.keySet()));
        assertTrue(new ArrayList<>(expected.values()).equals(new ArrayList<>(parsed.values())));
    }

    @Test
    public void shouldParseInvalidLine() {
        String line ="invalid line without separators";
        Map<String, String> parsed = new LineParser().parse(line);
        assertTrue(parsed.isEmpty());
    }
}
