package by.training.command;

import by.training.entity.BankDeposit;

import java.util.HashMap;
import java.util.Map;

public class BankDepositCommandProvider implements CommandProvider<BankDeposit> {

    private Map<CommandType, Command<BankDeposit>> commands = new HashMap<>();

    @Override
    public void register(CommandType type, Command<BankDeposit> command) {
        commands.put(type, command);
    }

    @Override
    public Command<BankDeposit> getCommand(CommandType type) {
        return commands.get(type);
    }
}