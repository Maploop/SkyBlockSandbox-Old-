package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.gui.ItemsGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(player.hasPermission("dungeons.admin")) {
                if(args.length == 0) {
                    new ItemsGUI(new PlayerMenuUtility(player)).open();
                } else if(args.length == 1) {
                    ItemsGUI.search.put(player, args[0]);
                    player.sendMessage(ChatColor.GREEN + "You opened the menu with search: §e" + args[0]);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new ItemsGUI(new PlayerMenuUtility(player)).open();
                        }
                    }.runTaskLater(Items.getInstance(), 3);
                }

                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You must be admin or higher to use this command.");
            }
        } else {
            commandSender.sendMessage("NO!");
        }
        return false;
    }
}
