package net.maploop.items.command.commands;

import net.maploop.items.gui.PianoGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.util.IUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_piano implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("items.piano")) {
                new PianoGUI(new PlayerMenuUtility(player)).open();
                return true;
            } else {
                player.sendMessage("§cOnly players with the §3MVP§b+++ §crank can use this feature!");
                player.sendMessage("§eYou can boy a rank at our store, by clicking the text below:");
                TextComponent comp2 = IUtil.makeClickableText("§6§l[STORE]", "§fClick to go to\n§b§nhttps://store.skyblocksandbox.net", ClickEvent.Action.OPEN_URL, "https://store.skyblocksandbox.net");
                player.spigot().sendMessage(comp2);
            }
        }
        return true;
    }
}
