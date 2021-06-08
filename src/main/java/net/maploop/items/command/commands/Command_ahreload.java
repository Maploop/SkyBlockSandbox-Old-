package net.maploop.items.command.commands;

import net.maploop.items.Items;
import net.maploop.items.auction.AuctionBrowserGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Command_ahreload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (((Player)commandSender).hasPermission("items.admin.reload")) {
                AuctionBrowserGUI.DISABLED = true;
                AuctionBrowserGUI.ITEMS.clear();
                commandSender.sendMessage("§8Reloading auctions...");
                Items.getInstance().loadAuctions();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        AuctionBrowserGUI.DISABLED = false;
                        commandSender.sendMessage("§aSuccessfully reloaded all auctions!");
                    }
                }.runTaskLater(Items.getInstance(), 20);
            } else {
                commandSender.sendMessage("§cYou must be ADMIN or higher to use this!");
            }
            return true;
        }
        AuctionBrowserGUI.DISABLED = true;
        AuctionBrowserGUI.ITEMS.clear();
        commandSender.sendMessage("§8Reloading auctions...");
        Items.getInstance().loadAuctions();
        new BukkitRunnable() {
            @Override
            public void run() {
                AuctionBrowserGUI.DISABLED = false;
                commandSender.sendMessage("§aSuccessfully reloaded all auctions!");
            }
        }.runTaskLater(Items.getInstance(), 20);
        return true;
    }
}
