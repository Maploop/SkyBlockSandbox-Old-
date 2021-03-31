package net.maploop.items.command.commands;

import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.itemCreator.ItemCreatorGUI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_createitem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("items.createitem")) {
                if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                    new ItemCreatorGUI(new PlayerMenuUtility(player)).open();
                    return true;
                }
                player.sendMessage("§cYou must have an item in your hand in order to create a custom item!");
            } else {
                player.sendMessage("§cYou must be admin or higher to use this command!");
            }
        }
        return false;
    }
}
