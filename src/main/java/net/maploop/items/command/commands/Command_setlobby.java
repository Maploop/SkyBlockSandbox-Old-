package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_setlobby implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("items.admin")){
                Items plugin = Items.getInstance();
                plugin.getData().set("data.locations.lobbyspawn.x", player.getLocation().getX());
                plugin.getData().set("data.locations.lobbyspawn.y", player.getLocation().getY());
                plugin.getData().set("data.locations.lobbyspawn.z", player.getLocation().getZ());
                plugin.getData().set("data.locations.lobbyspawn.world", player.getLocation().getWorld().getName());
                plugin.getData().set("data.locations.lobbyspawn.yaw", player.getLocation().getYaw());
                plugin.getData().set("data.locations.lobbyspawn.pitch", player.getLocation().getPitch());
                plugin.saveData();
                new User(player).playSound("random.anvil.land", 1, 2);
            } else {
                player.sendMessage("§cYou must be admin or higher to use this command!");
            }
        }
        return false;
    }
}
