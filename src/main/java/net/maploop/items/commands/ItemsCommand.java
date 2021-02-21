package net.maploop.items.commands;

import net.maploop.items.menus.ItemsMenu;
import net.maploop.items.menus.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You are not allowed to do that!");
            return true;
        }
        Player player = (Player) sender;

        if (label.equalsIgnoreCase("items")) {
            if (player.hasPermission("items.admin")) {
                new ItemsMenu(new PlayerMenuUtility(player)).open();
            } else {
                player.sendMessage(ChatColor.RED + "You are not allowed to do that!");
            }
        }

        return false;
    }
}
