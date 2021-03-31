package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.item.ItemUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_loop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("items.admin")){
                if(args.length == 0) {
                    player.sendMessage("§cMissing arguments!");
                    return true;
                }
                if(args.length == 1) {
                    player.sendMessage("§cMissing arguments!");
                    return true;
                }
                if(args.length == 2) {
                    if(ItemUtilities.isInteger(args[0])) {
                        int a = Integer.parseInt(args[0]);
                        for(int i = 0; i < a; ++i) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    player.performCommand(args[1]);
                                    player.sendMessage("§aSuccess!");
                                }
                            }, i);
                        }
                    } else {
                        player.sendMessage("§cInvalid number input!");
                    }
                }
                if(args.length == 3) {
                    if(ItemUtilities.isInteger(args[0])) {
                        int a = Integer.parseInt(args[0]);
                        for(int i = 0; i < a; ++i) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    player.performCommand(args[1] + " " + args[2]);
                                    player.sendMessage("§aSuccess!");
                                }
                            }, i);
                        }
                    } else {
                        player.sendMessage("§cInvalid number input!");
                    }
                }
                if(args.length == 4) {
                    if(ItemUtilities.isInteger(args[0])) {
                        int a = Integer.parseInt(args[0]);
                        for(int i = 0; i < a; ++i) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    player.performCommand(args[1] + " " + args[2] + " " + args[3]);
                                    player.sendMessage("§aSuccess!");
                                }
                            }, i);
                        }
                    } else {
                        player.sendMessage("§cInvalid number input!");
                    }
                }
                if(args.length == 5) {
                    if(ItemUtilities.isInteger(args[0])) {
                        int a = Integer.parseInt(args[0]);
                        for(int i = 0; i < a; ++i) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Items.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    player.performCommand(args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
                                    player.sendMessage("§aSuccess!");
                                }
                            }, i);
                        }
                    } else {
                        player.sendMessage("§cInvalid number input!");
                    }
                }
            } else {
                player.sendMessage("§cYou must be admin or higher to use this command!");
            }
        }
        return false;
    }
}
