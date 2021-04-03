package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUIUpdateEvent;
import net.maploop.items.gui.ItemsGUI;
import net.maploop.items.gui.MinecraftItemsGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.itemCreator.StatsEditorGUI;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.Attribute;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SignGUIUpdateListener implements Listener {
    @EventHandler
    public void onUpdate(SignGUIUpdateEvent event) {
        Player player = event.getPlayer();
        if(ItemsGUI.searching.contains(player)) {
            if(event.getSignText()[0] == null) {
                player.sendMessage("§cInvalid search!");
                return;
            }
            ItemsGUI.search.put(player, event.getSignText()[0]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    new ItemsGUI(new PlayerMenuUtility(player)).open();
                    ItemsGUI.searching.remove(player);
                }
            }.runTaskLater(Items.getInstance(), 2);
            return;
        }
        if(MinecraftItemsGUI.mcSearching.contains(player)) {
            if(event.getSignText()[0] == null) {
                player.sendMessage("§cInvalid search!");
                return;
            }
            MinecraftItemsGUI.mcSearch.put(player, event.getSignText()[0]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    MinecraftItemsGUI.mcSearching.remove(player);
                }
            }.runTaskLater(Items.getInstance(), 2);
        }
        /** For item stat changes:*/
        else if (StatsEditorGUI.healthChange.contains(player)) {
            if(ItemUtilities.isInteger(event.getSignText()[0]) && event.getSignText()[0] != null) {
                ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "is-SB", "true");
                ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getSignText()[0]), Attribute.HEALTH.toString());
                ItemMeta hmeta = h.getItemMeta();
                List<String> lore;
                if(hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                else lore = new ArrayList<>();
                lore.add("§7Health: §a+" + event.getSignText()[0]);
                hmeta.setLore(lore);
                h.setItemMeta(hmeta);

                player.setItemInHand(h);
            } else {
                player.sendMessage("§cThat's not a valid number!");
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                player.closeInventory();
            }
        }
    }
}
