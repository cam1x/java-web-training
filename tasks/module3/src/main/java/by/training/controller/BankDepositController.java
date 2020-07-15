package by.training.controller;

import by.training.command.Command;
import by.training.command.CommandException;
import by.training.command.CommandType;
import by.training.command.BankDepositCommandProvider;
import by.training.entity.BankDeposit;
import by.training.service.Service;
import by.training.validator.FileValidator;
import by.training.validator.ValidationResult;
import by.training.validator.XMLValidator;

import org.apache.log4j.Logger;

import java.util.List;

public class BankDepositController {
    private static final Logger LOGGER = Logger.getLogger(BankDepositController.class);

    private FileValidator fileValidator;
    private XMLValidator xmlValidator;
    private BankDepositCommandProvider provider;
    private Service<BankDeposit> service;

    public BankDepositController(FileValidator fileValidator, XMLValidator xmlValidator,
                                 BankDepositCommandProvider provider, Service<BankDeposit> service) {
        this.fileValidator = fileValidator;
        this.xmlValidator = xmlValidator;
        this.provider = provider;
        this.service = service;
    }

    public void buildFromXML(String path, CommandType commandType)  {
        ValidationResult fileValidationRes = fileValidator.validate(path);
        if (!fileValidationRes.isValid()) {
            LOGGER.error(fileValidationRes);
            return;
        }

        ValidationResult xmlValidationRes = xmlValidator.validate(path);
        if (!xmlValidationRes.isValid()) {
            LOGGER.error(xmlValidationRes);
            return;
        }

        Command<BankDeposit> command = provider.getCommand(commandType);

        List<BankDeposit> depositList;
        try {
            depositList = command.build(path);
        } catch (CommandException e) {
            throw new ControllerException(e);
        }

        depositList.forEach(service::save);
    }
}
