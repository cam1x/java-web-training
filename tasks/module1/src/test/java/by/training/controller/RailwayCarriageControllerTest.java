package by.training.controller;

import by.training.builder.BuilderFactory;
import by.training.parser.LineParser;
import by.training.reader.DataFileReader;
import by.training.repository.CarriageRepository;
import by.training.service.RailwayCarriageService;

import by.training.validator.DataValidator;
import by.training.validator.FileValidator;
import by.training.validator.LineFormValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RailwayCarriageControllerTest {

    private static RailwayCarriageController controller;

    @BeforeClass
    public static void prepare() {
        RailwayCarriageService service = new RailwayCarriageService(new CarriageRepository());
        FileValidator fileValidator = new FileValidator();
        DataFileReader dataFileReader = new DataFileReader();
        LineFormValidator lineFormValidator = new LineFormValidator();
        LineParser lineParser = new LineParser();
        BuilderFactory builderFactory = new BuilderFactory();
        DataValidator dataValidator = new DataValidator();

        controller = new RailwayCarriageController(service, fileValidator, dataFileReader,
                lineFormValidator, lineParser, builderFactory, dataValidator);
    }

    @Test
    public void shouldReadNothingFromNotExistingFile() {
        String path = "NoPath.txt";
        assertEquals(0, controller.readFromFile(path));
    }

    @Test
    public void shouldReadNothingFromEmptyFile() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("Empty.txt").getFile()).getAbsolutePath();
        assertEquals(0, controller.readFromFile(path));
    }

    @Test
    public void shouldReadAllFile() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("FullyValid.txt").getFile()).getAbsolutePath();
        assertEquals(4, controller.readFromFile(path));
    }

    @Test
    public void shouldReadPartFromFileFirst() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("PartlyValid1.txt").getFile()).getAbsolutePath();
        assertEquals(2, controller.readFromFile(path));
    }

    @Test
    public void shouldReadPartFromFileSecond() {
        String path = new File(this.getClass().getClassLoader()
                .getResource("PartlyValid2.txt").getFile()).getAbsolutePath();
        assertEquals(1, controller.readFromFile(path));
    }
}