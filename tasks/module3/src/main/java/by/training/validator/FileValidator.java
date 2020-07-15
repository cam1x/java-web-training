package by.training.validator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileValidator {

    public ValidationResult validate(String path) {
        ValidationResult validationResult = new ValidationResult();
        File dataFile = new File(path);

        if (!dataFile.exists()) {
            validationResult.addError("Wrong path","File doesn't exist!");
            return validationResult;
        }

        if (!Files.isReadable(Paths.get(path))) {
            validationResult.addError("Wrong file","File is not readable!");
            return validationResult;
        }

        if (dataFile.length()==0) {
            validationResult.addError("Wrong file","File is empty!");
        }

        return validationResult;
    }
}