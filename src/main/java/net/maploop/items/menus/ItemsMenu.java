package net.maploop.items.menus;

import net.maploop.items.Items;
import net.maploop.items.helpers.Search;
import net.maploop.items.items.*;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemsMenu extends Menu {
    public ItemsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Select an Item";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§cClose":
                player.closeInventory();
                break;
            case "§aSearch items":
                player.closeInventory();
                player.sendMessage("§cUnavailable.");
                break;
            case "§6Hyperion":
                player.getInventory().addItem(HYPERION.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
            case "§aRadiant Power Orb":
                player.getInventory().addItem(RADIANT_POWER_ORB.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
            case "§9Mana Flux Power Orb":
                player.getInventory().addItem(MANAFLUX_POWER_ORB.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
            case "§5Overflux Power Orb":
                player.getInventory().addItem(OVERFLUX_POWER_ORB.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
            case "§aGrappling Hook":
                player.getInventory().addItem(GRAPPLING_HOOK.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
            case "§6Plasmaflux Power Orb":
                player.getInventory().addItem(PLASMAFLUX_POWER_ORB.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
        }
    }

    @Override
    public void setItems() {
        fillBottom();
        ItemStack close = makeItem(Material.BARRIER, "§cClose", 1, 0, "§7Close the menu.");
        ItemStack search = makeItem(Material.SIGN, "§aSearch items", 1, 0, "§7Find the item of", "§7your choice by searching", "", "§cFeature Unavailable");
        inventory.setItem(49, close);
        inventory.setItem(50, search);

        inventory.addItem(HYPERION.get(), RADIANT_POWER_ORB.get(), MANAFLUX_POWER_ORB.get(), OVERFLUX_POWER_ORB.get(), PLASMAFLUX_POWER_ORB.get(), GRAPPLING_HOOK.get());
    }

    private void search(Player player) {
        Search search = new Search(Items.getInstance());
        search.open(player, new String[] {"", "^^^^^", "Type your search"}, new Search.SignGUIListener() {

            @Override
            public void onSignDone(Player player, String[] lines) {
                player.sendMessage(lines[0]);
            }
        });
    }
}
