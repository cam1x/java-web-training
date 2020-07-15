package by.training.controller;

import by.training.model.TextComposite;
import by.training.model.TextPartTypes;
import by.training.parser.ParagraphParser;
import by.training.reader.FromFileReader;
import by.training.service.TextService;
import by.training.validator.FileValidator;
import by.training.validator.ValidationResult;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

public class TextController {

    private static final Logger LOGGER = Logger.getLogger(TextController.class);

    private TextService service;
    private FileValidator fileValidator;
    private FromFileReader reader;
    private ParagraphParser paragraphParser;

    public TextController(TextService service, FileValidator fileValidator,
                          FromFileReader reader, ParagraphParser paragraphParser) {
        this.service = service;
        this.fileValidator = fileValidator;
        this.reader = reader;
        this.paragraphParser = paragraphParser;
    }

    /**
     * validate, parse, and save text
     * from path
     * @param path path to file with text
     */
    public void saveTextFromFile(String path) {
        ValidationResult validationResult = fileValidator.validate(path);

        if (!validationResult.isValid()) {
            LOGGER.error(validationResult);
            return;
        }

        String text = reader.readInString(path);
        TextComposite wholeText = paragraphParser.parseLine(text);

        service.save(wholeText);
    }

    /**
     * Sorts saved in repo text
     * @param type sortable type
     * @param comparator sort term
     * @return Text with sorted parts,
     * if repo is empty returns empty composite
     */
    public TextComposite sort(TextPartTypes type, Comparator<TextComposite> comparator) {
        return service.sort(type, comparator);
    }

    /**
     * finds text composites by predicate in repo
     * @param predicate search term
     * @return found TextComposites
     */
    public List<TextComposite> find(Predicate<TextComposite> predicate) {
        return service.find(predicate);
    }
}
