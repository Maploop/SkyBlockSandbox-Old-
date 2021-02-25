package net.maploop.items.commands;

import net.maploop.items.menus.PlayerMenuUtility;
import net.maploop.items.menus.ReforgingMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReforgingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        if (s.equalsIgnoreCase("reforging")) {
            new ReforgingMenu(new PlayerMenuUtility(player)).open();
        }
        return false;
    }
}
