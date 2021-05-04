package net.maploop.items.command.commands;

import net.maploop.items.command.AbstractCommand;
import net.maploop.items.command.CommandParameters;
import net.maploop.items.command.CommandUser;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.ShutUpGUI;
import net.maploop.items.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_shutup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("items.admin")) {
                new ShutUpGUI(new PlayerMenuUtility(player)).open();
            } else {
                player.sendMessage("§cThis command is only available to admins.");
            }
        } else {
            sender.sendMessage("§aNo!");
        }

        return true;
    }
}
