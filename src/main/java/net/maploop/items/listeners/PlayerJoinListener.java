package net.maploop.items.listeners;

import net.maploop.items.items.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ItemStack item = ItemMaker.makeItem(Material.NETHER_STAR, "§aSkyblock Menu", 1, 0);
        player.getInventory().setItem(8, item);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (!(event.getCurrentItem().hasItemMeta())) return;
        if (!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aSkyblock Menu")) {
            List<ItemStack> prevents = new ArrayList<>();
            prevents.add(event.getCurrentItem());
            prevents.add(event.getCursor());
            prevents.add((event.getClick() == org.bukkit.event.inventory.ClickType.NUMBER_KEY) ?
                    event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) : event.getCurrentItem());
            for (ItemStack item : prevents) {
                if (item != null && item.hasItemMeta()) {
                    event.setCancelled(true);
                }
            }
            event.setCancelled(true);
        }
    }
}
