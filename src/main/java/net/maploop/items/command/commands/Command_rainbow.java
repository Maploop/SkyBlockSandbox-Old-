package net.maploop.items.command.commands;

import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.RainbowGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_rainbow implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        new RainbowGUI(new PlayerMenuUtility(((Player)commandSender))).open();
        return false;
    }
}
