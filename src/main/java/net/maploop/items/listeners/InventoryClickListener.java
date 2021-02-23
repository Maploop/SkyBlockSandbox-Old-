package net.maploop.items.listeners;

import net.maploop.items.menus.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (event.getInventory().getTitle().contains("Profile")) event.setCancelled(true);
        if (event.getInventory().getTitle().contains("Auction")) event.setCancelled(true);
        if (holder instanceof Menu) {
            event.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.hadleMenu(event);
        }
    }
}
