package net.maploop.items.commands;

import net.maploop.items.menus.PetsMenu;
import net.maploop.items.menus.PlayerMenuUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("pets")) {
            new PetsMenu(new PlayerMenuUtility((player))).open();
        }
        return false;
    }
}
