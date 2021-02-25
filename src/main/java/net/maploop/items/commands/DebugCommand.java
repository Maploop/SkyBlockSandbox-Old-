package net.maploop.items.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DebugCommand implements CommandExecutor {
    public static Map<UUID, Boolean> debug = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!(debug.containsKey(player.getUniqueId()))) {
            debug.put(player.getUniqueId(), true);
            player.sendMessage("§aYou are now in debug mode.");
        } else {
            debug.remove(player.getUniqueId());
            player.sendMessage("§cYou are no longer in debug mode.");
        }
        return false;
    }
}
