package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.item.items.BlockZapper;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_setspawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Items plugin = Items.getInstance();
            plugin.getConfig().set("locations.lobbyspawn.x",player.getLocation().getX());
            plugin.getConfig().set("locations.lobbyspawn.y",player.getLocation().getY());
            plugin.getConfig().set("locations.lobbyspawn.z",player.getLocation().getZ());
            plugin.getConfig().set("locations.lobbyspawn.world",player.getLocation().getWorld().getName());
            plugin.getConfig().set("locations.lobbyspawn.yaw",player.getLocation().getYaw());
            plugin.getConfig().set("locations.lobbyspawn.pitch",player.getLocation().getPitch());
            plugin.saveConfig();
        } else {
            sender.sendMessage("No!!?!?!?!?!");
        }
        return false;
    }
}
