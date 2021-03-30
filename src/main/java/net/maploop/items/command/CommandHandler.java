package net.maploop.items.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    private List<AbstractCommand> commands;

    public CommandHandler() {
        commands = new ArrayList<>();
    }

    public void add(AbstractCommand command) {
        commands.add(command);
        command.register();
    }

    public int getCommandAmount() {
        return commands.size();
    }
}
