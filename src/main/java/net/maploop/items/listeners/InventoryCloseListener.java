package net.maploop.items.listeners;

import net.maploop.items.data.BackpackData;
import net.maploop.items.item.ItemUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onClose(InventoryCloseEvent event) {
        if(event.getInventory().getTitle().contains("Backpack")) {
            if(BackpackData.inv.containsKey(event.getPlayer().getUniqueId())) {
                String uuid = ItemUtilities.getStringFromItem(BackpackData.inv.get(event.getPlayer().getUniqueId()), "UUID");
                BackpackData.getData().put(uuid, event.getInventory().getContents());
                BackpackData.inv.remove(event.getPlayer().getUniqueId());
            }
        }
    }
}
