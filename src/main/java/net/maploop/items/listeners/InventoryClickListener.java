package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.data.BackpackData;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PianoGUI;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClick() == ClickType.NUMBER_KEY) {
            if (PianoGUI.playingWithKeyboard.contains((Player) event.getWhoClicked())) {
                event.setCancelled(true);
                switch (event.getHotbarButton()) {
                    case 0: {
                        // C
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.707107f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                    case 1: {
                        // D
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.793701f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                    case 2: {
                        // E
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.890899f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                    case 3: {
                        // F
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.943874f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                    case 4: {
                        // G
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.059463f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                    case 5: {
                        // A
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.189207f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                    case 6: {
                        // B
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.334840f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                    case 7: {
                        // c #1
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.414214f);
                        player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                        break;
                    }
                }
            }
        }


        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof GUI) {
            GUI GUI = (GUI) holder;
            GUI.hadleMenu(event);
        }
        try {
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {

                if (ItemUtilities.getIntFromItem(event.getCurrentItem(), "SB-ID") == 20) {
                    if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                        event.setCancelled(true);
                    }
                    if (event.getInventory().getTitle().contains("Backpack") || event.getInventory().getTitle().contains("Profile") || event.getInventory().getTitle().contains("Trade with")) {
                        event.setCancelled(true);
                    } else {
                        if (player.getGameMode().equals(GameMode.CREATIVE)) {
                            player.sendMessage("§cYou cannot use creative with this item!");
                            return;
                        }
                        if (event.isRightClick()) {
                            event.setCancelled(true);
                            String uuid = ItemUtilities.getStringFromItem(event.getCurrentItem(), "UUID");
                            player.playSound(player.getLocation(), Sound.HORSE_ARMOR, 1f, 1f);

                            Inventory backPackinv = Bukkit.createInventory(player, 9 * 3, "Large Backpack");
                            if (BackpackData.getData().containsKey(uuid)) {
                                backPackinv.setContents(BackpackData.getData().get(uuid));
                                player.openInventory(backPackinv);
                                BackpackData.inv.put(player.getUniqueId(), event.getCurrentItem());
                            }
                            BackpackData.inv.put(player.getUniqueId(), event.getCurrentItem());
                            player.openInventory(backPackinv);
                        }
                    }
                } else {
                    if (player.getOpenInventory().getTitle().contains("Backpack")) {
                        try {
                            if (ItemUtilities.getIntFromItem(event.getCurrentItem(), "SB-ID") == 20) {
                                if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
                                    event.setCancelled(true);
                                }
                            }
                        } catch (NullPointerException e) {

                        }
                    }
                }
            }
        } catch (NullPointerException e) {
        }
    }
}
