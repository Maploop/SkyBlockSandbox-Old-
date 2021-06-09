package net.maploop.items.command.commands;

import net.maploop.items.auction.AuctionHouseGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.mongo.MongoConnect;
import net.maploop.items.util.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_ah implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (new MongoConnect().isConnected()) {
                new AuctionHouseGUI(new PlayerMenuUtility(player)).open();
            } else {
                player.sendMessage("§cError occurred while connecting to the databse, please try again later.");
                Log.warn("Failed to open Auction menu for " + player.getName() + "!");
            }
        }

        return true;
    }
}
