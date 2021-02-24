package net.maploop.items.commands;

import net.maploop.items.helpers.Maps;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MysearchCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (s.equalsIgnoreCase("mysearch")) {
            player.sendMessage(ChatColor.GREEN + Maps.searching.get(player.getUniqueId()));
        }
        return false;
    }
}
