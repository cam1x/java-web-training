package by.training.controller;

import by.training.builder.BuilderFactory;
import by.training.entity.RailwayCarriage;
import by.training.reader.DataFileReader;
import by.training.parser.LineParser;
import by.training.service.RailwayCarriageService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import by.training.validator.DataValidator;
import by.training.validator.FileValidator;
import by.training.validator.LineFormValidator;
import by.training.validator.ValidationResult;

import org.apache.log4j.Logger;

public class RailwayCarriageController {

    private static final Logger LOGGER = Logger.getLogger(RailwayCarriageController.class);

    private RailwayCarriageService service;
    private FileValidator fileValidator;
    private DataFileReader dataFileReader;
    private LineFormValidator lineFormValidator;
    private LineParser lineParser;
    private BuilderFactory builderFactory;
    private DataValidator dataValidator;

    public RailwayCarriageController(RailwayCarriageService service, FileValidator fileValidator,
                                     DataFileReader dataFileReader, LineFormValidator lineFormValidator,
                                     LineParser lineParser, BuilderFactory builderFactory,
                                     DataValidator dataValidator) {
        this.service = service;
        this.fileValidator = fileValidator;
        this.dataFileReader = dataFileReader;
        this.lineFormValidator = lineFormValidator;
        this.lineParser = lineParser;
        this.builderFactory = builderFactory;
        this.dataValidator = dataValidator;
    }

    /**
    Returns num of valid (read) lines from file;
    If all file lines are invalid returns 0
     */
    public int readFromFile(String path) {
        ValidationResult validationResult = fileValidator.validate(path);

        if (!validationResult.isValid()){
            LOGGER.error(validationResult);
            return 0;
        }

        int numOfRead = 0;

        try {
            List<String> fileLines = dataFileReader.read(path);
            for (int lineNum=0;lineNum<fileLines.size();lineNum++) {
                ValidationResult validationLineResult =
                       lineFormValidator.validate(fileLines.get(lineNum));
                if (!validationLineResult.isValid()) {
                    LOGGER.error("Line " + (lineNum + 1) + ": " + validationLineResult);
                    continue;
                }
                Map<String, String> parsed =
                        lineParser.parse(fileLines.get(lineNum));
                ValidationResult validationDataResult = dataValidator.validate(parsed);
                if (!validationDataResult.isValid()) {
                    LOGGER.error("Line "+(lineNum+1)+": "+validationDataResult);
                    continue;
                }
                RailwayCarriage entity = builderFactory.getByType(parsed.get("type")).get().build(parsed);
                service.save(entity);
                numOfRead++;
                LOGGER.info("Line "+(lineNum+1)+": "+"Entity successfully created");
            }
        }
        catch (IOException e){
            LOGGER.error("FileReader error: "+"Unable to read file: " + e);
        }

        return numOfRead;
    }
}