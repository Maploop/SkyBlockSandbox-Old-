package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.item.CustomItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Command_sbclear implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("items.clearinv")) {
                player.getInventory().clear();
                player.sendMessage("§aZoop!");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getInventory().setItem(8, CustomItem.fromString(Items.getInstance(), "skyblock_menu", 1));
                    }
                }.runTaskLater(Items.getInstance(), 1);
            }
        }
        return false;
    }
}
