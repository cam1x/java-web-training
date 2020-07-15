package by.training.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class FromFileReader {

    private static final Logger LOGGER = Logger.getLogger(FromFileReader.class);

    public String readInString(String path) {
        String string = "";
        try {
            string = Files.readAllLines(Paths.get(path)).stream().collect(Collectors.joining("\n")).replaceAll("\\s+$","");
        } catch (IOException ex) {
            LOGGER.error("Cannot read from file: " + ex);
        }
        return string;
    }
}
