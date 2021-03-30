package net.maploop.items.command.commands;

import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("items.admin")) {
                if(args.length == 0) {
                    player.sendMessage("&cMissing arguments!".replace("&", "§"));
                    return true;
                }
                if(args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "s":
                        case "survival":
                        case "0": {
                            player.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage("Set own gamemode to SURVIVAL mode.");
                            break;
                        }
                        case "c":
                        case "creative":
                        case "1": {
                            player.setGameMode(GameMode.CREATIVE);
                            player.sendMessage("Set own gamemode to CREATIVE mode.");
                            break;
                        }
                        case "a":
                        case "adventure":
                        case "2": {
                            player.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage("Set own gamemode to ADVENTURE mode.");
                            break;
                        }
                        case "sp":
                        case "spectator":
                        case "3": {
                            player.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage("Set own gamemode to SPECTATOR mode.");
                            break;
                        }
                    }
                    return true;
                }
                if(args.length == 2) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target.isOnline()) {
                        switch (args[0].toLowerCase()) {
                            case "s":
                            case "survival":
                            case "0": {
                                target.setGameMode(GameMode.SURVIVAL);
                                target.sendMessage("Your gamemode was set to SURVIVAL mode.");
                                break;
                            }
                            case "c":
                            case "creative":
                            case "1": {
                                target.setGameMode(GameMode.CREATIVE);
                                target.sendMessage("Your gamemode was set to CREATIVE mode.");
                                break;
                            }
                            case "a":
                            case "adventure":
                            case "2": {
                                target.setGameMode(GameMode.ADVENTURE);
                                target.sendMessage("Your gamemode was set to ADVENTURE mode.");
                                break;
                            }
                            case "sp":
                            case "spectator":
                            case "3": {
                                target.setGameMode(GameMode.SPECTATOR);
                                target.sendMessage("Your gamemode was set to SPECTATOR mode.");
                                break;
                            }
                        }
                    } else {
                        player.sendMessage(IUtil.colorize("&cThat player is offline!"));
                    }
                    return true;
                }

            } else {
                player.sendMessage(IUtil.colorize("&cYou must be admin or higher to use this command!"));
            }
        } else {
            commandSender.sendMessage("No!");
        }
        return false;
    }
}
