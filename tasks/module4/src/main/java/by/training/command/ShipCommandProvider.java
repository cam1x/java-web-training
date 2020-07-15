package by.training.command;

import by.training.thread.Ship;

import java.util.HashMap;
import java.util.Map;

public class ShipCommandProvider implements CommandProvider<Ship> {
    private Map<CommandType, Command<Ship>> commands = new HashMap<>();

    @Override
    public void register(CommandType type, Command<Ship> command) {
        commands.put(type, command);
    }

    @Override
    public Command<Ship> getCommand(CommandType type) {
        return commands.get(type);
    }
}