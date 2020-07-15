package by.training.controller;

import by.training.command.*;
import by.training.thread.Ship;
import by.training.validation.FileValidator;
import by.training.validation.ValidationResult;
import by.training.validation.XMLValidator;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShipController {
    private static final Logger LOGGER = Logger.getLogger(ShipController.class);

    private FileValidator fileValidator;
    private XMLValidator shipXMLValidator;
    private XMLValidator poolXMLValidator;
    private CommandProvider<Ship> provider;

    public ShipController(FileValidator fileValidator, XMLValidator shipXMLValidator,
                          XMLValidator poolXMLValidator, CommandProvider<Ship> provider) {
        this.fileValidator = fileValidator;
        this.shipXMLValidator = shipXMLValidator;
        this.poolXMLValidator = poolXMLValidator;
        this.provider = provider;
    }

    /**
     * Build ships from xml and run them
     *
     * @param shipXMLPath income path to xml with ships
     * @param poolXMLPath income path to xml with pool
     * @param commandType type of parsing command
     * @return list of built ships
     * @throws ControllerException if failed file/xml validation
     *         or if ships building
     */
    public List<Ship> readFromXMLAndRun(String shipXMLPath, String poolXMLPath, CommandType commandType) {
        ValidationResult fileValidationRes = fileValidator.validate(shipXMLPath);
        fileValidationRes.and(fileValidator.validate(poolXMLPath));
        if (!fileValidationRes.isValid()) {
            LOGGER.error(fileValidationRes);
            throw new ControllerException("Not valid income file: " + fileValidationRes);
        }

        ValidationResult xmlValidationRes = shipXMLValidator.validate(shipXMLPath);
        xmlValidationRes.and(poolXMLValidator.validate(poolXMLPath));
        if (!xmlValidationRes.isValid()) {
            LOGGER.error(xmlValidationRes);
            throw new ControllerException("Not valid income xml: " + xmlValidationRes);
        }

        Command<Ship> command = provider.getCommand(commandType);

        List<Ship> ships;
        try {
            ships = command.build(shipXMLPath, poolXMLPath);
        } catch (CommandException e) {
            throw new ControllerException(e);
        }

        ExecutorService service = Executors.newFixedThreadPool(ships.size());
        ships.forEach(service::execute);
        return ships;
    }
}
