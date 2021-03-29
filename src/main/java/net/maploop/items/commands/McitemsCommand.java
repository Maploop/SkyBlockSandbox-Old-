package net.maploop.items.commands;

import net.maploop.items.gui.MinecraftItemsGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class McitemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("mcitems.use")) {
                new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
            } else {
                player.sendMessage("§cYou must be admin or higher to use this command.");
            }
        }
        return false;
    }
}
