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
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                if (player.hasPermission("mcitems.use")) {
                    if (Stream.of(Material.STAINED_CLAY, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE, Material.INK_SACK, Material.WOOL).anyMatch(material -> player.getItemInHand().getType().equals(material)) && player.getItemInHand().hasItemMeta()) {
                        new DyeGUI(new PlayerMenuUtility(player), player.getItemInHand()).open();
                    } else if (Stream.of(Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE).anyMatch(material -> player.getItemInHand().getType().equals(material))) {
                        player.performCommand("dyearmor");
                    } else {
                        player.sendMessage("§cYou dont have an item in your hand that can be dyed!");
                    }
                } else {
                    player.sendMessage("§cYou must be admin or higher to use this command.");
                }
            } else {
                if(args.length == 1) {
                    for (String arg : args) {
                        if (!(arg.toUpperCase().matches(("[0-9,A-F]+")))) {
                            String line = "dyearmor " + args[0];
                            player.performCommand(line);
                            return true;
                        }
                    }
                } else if(args.length == 3){
                    for (String arg : args) {
                        if (!(Integer.valueOf(arg) < 256)) {
                            String line = "dyearmor" + args[0] + " " + args[1] + " " + args[2];
                            player.performCommand(line);
                        }
                    }
                }
            }
        }
        return false;
    }
}