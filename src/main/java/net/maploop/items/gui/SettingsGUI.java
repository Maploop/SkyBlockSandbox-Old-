package net.maploop.items.gui;

import net.maploop.items.data.DataHandler;
import net.maploop.items.util.DUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsGUI extends GUI {
    public SettingsGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Settings";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        DataHandler handler = new DataHandler((Player) event.getWhoClicked());

        switch (event.getSlot()) {
            case 19: {
                if(handler.getProfileViewer()) {
                    handler.setProfileViewer(false);
                    playerMenuUtility.getOwner().playSound(playerMenuUtility.getOwner().getLocation(), Sound.NOTE_PLING, 1, 2);
                    setItems();
                    playerMenuUtility.getOwner().openInventory(inventory);
                    break;
                } else {
                    handler.setProfileViewer(true);
                    playerMenuUtility.getOwner().playSound(playerMenuUtility.getOwner().getLocation(), Sound.NOTE_PLING, 1, 2);
                    setItems();
                    playerMenuUtility.getOwner().openInventory(inventory);
                    break;
                }
            }
            case 48: {
                new SkyblockMenuGUI(playerMenuUtility).open();
                break;
            }
            default: {
                event.setCancelled(true);
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        DataHandler handler = new DataHandler(playerMenuUtility.getOwner());
        inventory.setItem(48, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Skyblock Menu"));
        inventory.setItem(49, makeItem(Material.BARRIER, "§cClose", 1, 0));

        inventory.setItem(10, makeItem(Material.SKULL_ITEM, "§aProfile Viewer", 1, 0, DUtil.colorize("&7Player player's profiles on\n&7right-click.")));
        if(handler.getProfileViewer()) {
            inventory.setItem(19, makeItem(Material.INK_SACK, "§aProfile Viewer", 1, 10, DUtil.colorize("&7Click to disable!")));
        } else {
            inventory.setItem(19, makeItem(Material.INK_SACK, "§aProfile Viewer", 1, 8, DUtil.colorize("&7Click to enable!")));
        }
    }
}
