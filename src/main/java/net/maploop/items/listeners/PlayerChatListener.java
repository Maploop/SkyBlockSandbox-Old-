package net.maploop.items.listeners;

import net.maploop.items.gui.ItemsGUI;
import net.maploop.items.gui.MinecraftItemsGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();

        if(ItemsGUI.searching.contains(player)) {
            event.setCancelled(true);
            ItemsGUI.search.put(player, event.getMessage());

            player.sendMessage(ChatColor.GREEN + "You're search is: " + ChatColor.YELLOW + ItemsGUI.search.get(player));
            ItemsGUI.searching.remove(player);
            new ItemsGUI(new PlayerMenuUtility(player)).open();
        }

        if(MinecraftItemsGUI.mcSearching.contains(player)) {
            event.setCancelled(true);
            MinecraftItemsGUI.mcSearch.put(player, event.getMessage());

            player.sendMessage("§aYour search query is: " + ChatColor.YELLOW + MinecraftItemsGUI.mcSearch.get(player));
            MinecraftItemsGUI.mcSearching.remove(player);
            new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
        }
    }
}
