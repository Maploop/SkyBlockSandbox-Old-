package net.maploop.items.command.commands;

import net.maploop.items.command.AbstractCommand;
import net.maploop.items.command.CommandParameters;
import net.maploop.items.command.CommandSource;
import net.maploop.items.command.CommandUser;
import net.maploop.items.user.User;
import org.bukkit.entity.Player;

@CommandParameters(description = "Just testing", source = CommandSource.IN_GAME, usage = "/<command> <w>")
public class Command_test extends AbstractCommand {
    @Override
    public void run(CommandUser sender, User user, String[] args) {
        Player player = user.getBukkitPlayer();
        player.sendMessage("§aExecuted test.");
    }
}
