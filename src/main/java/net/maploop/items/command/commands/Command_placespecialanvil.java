package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.enums.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Command_placespecialanvil implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Message.ONLY_IN_GAME.getMessage());
            return true;
        }

        if (!sender.hasPermission("items.placesbanvil")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return true;
        }

        Player player = (Player) sender;
        Location location = player.getLocation();

        Block block = location.getBlock();

        block.setType(Material.ANVIL);
        block.setMetadata("reforge_anvil", new FixedMetadataValue(Items.getInstance(), "true"));

        player.sendMessage("Placed!");

        return true;
    }
}
