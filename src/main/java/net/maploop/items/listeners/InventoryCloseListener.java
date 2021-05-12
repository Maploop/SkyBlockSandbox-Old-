package net.maploop.items.listeners;

import net.maploop.items.data.BackpackData;
import net.maploop.items.gui.PianoGUI;
import net.maploop.items.item.ItemUtilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

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
        Player player = (Player) event.getPlayer();

        if(event.getInventory().getTitle().contains("Piano")) {
            if(PianoGUI.playingWithKeyboard.contains(player)) {
                player.getInventory().setItem(0, new ItemStack(Material.AIR));
                player.getInventory().setItem(1, new ItemStack(Material.AIR));
                player.getInventory().setItem(2, new ItemStack(Material.AIR));
                player.getInventory().setItem(3, new ItemStack(Material.AIR));
                player.getInventory().setItem(4, new ItemStack(Material.AIR));
                player.getInventory().setItem(5, new ItemStack(Material.AIR));
                player.getInventory().setItem(6, new ItemStack(Material.AIR));
                player.getInventory().setItem(7, new ItemStack(Material.AIR));

                PianoGUI.playingWithKeyboard.remove(player);
            }
        }
    }
}
