package net.maploop.items.listeners;

import net.maploop.items.enums.Rarity;
import net.maploop.items.item.ItemUtilities;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemPickupListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        List<String> lore = new ArrayList<>();
        if(ItemUtilities.hasRarity(item)) return;

        event.getItem().remove();
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1f, 1f);
        ItemStack newItem = ItemUtilities.storeStringInItem(item, Rarity.COMMON.toString(), "Rarity");
        ItemMeta newitemMeta = newItem.getItemMeta();
        lore.add("§f§lCOMMON");
        newitemMeta.setLore(lore);
        newItem.setItemMeta(newitemMeta);
        player.getInventory().addItem(newItem);
    }
}
