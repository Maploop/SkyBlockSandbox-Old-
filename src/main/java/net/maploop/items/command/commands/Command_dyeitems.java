package net.maploop.items.command.commands;

import net.maploop.items.gui.itemCreator.DyeGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

public class Command_dyeitems implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("mcitems.use")) {
                if(Stream.of(Material.STAINED_CLAY, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE, Material.INK_SACK, Material.WOOL).anyMatch(material -> player.getItemInHand().getType().equals(material)) && player.getItemInHand().hasItemMeta()){
                    new DyeGUI(new PlayerMenuUtility(player),player.getItemInHand()).open();
                } else {
                    player.sendMessage("§cYou dont have an item in your hand that can be dyed!");
                }
            } else {
                player.sendMessage("§cYou must be admin or higher to use this command.");
            }
        }
        return false;
    }
}