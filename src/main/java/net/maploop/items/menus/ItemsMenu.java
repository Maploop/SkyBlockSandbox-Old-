package net.maploop.items.menus;

import com.sun.corba.se.spi.orb.ORBVersionFactory;
import net.maploop.items.Items;
import net.maploop.items.api.SignGUI;
import net.maploop.items.commands.DebugCommand;
import net.maploop.items.helpers.Maps;
import net.maploop.items.helpers.Search;
import net.maploop.items.helpers.Utilities;
import net.maploop.items.items.*;
import net.maploop.items.items.pets.bee.BEE_PET_COMMON;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Locale;

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
        if (DebugCommand.debug.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase()));
            player.sendMessage("The message above is the item you clicked.");
            Utilities.sendActionbar(player, "§eDisable the message you recived with §c/debug§e.");
        }

        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§cClose":
                player.closeInventory();
                break;
            case "§aSearch items":
                player.closeInventory();
                search();
                break;
            case "§cReset Search":
                Maps.searching.remove(player.getUniqueId());
                new ItemsMenu(new PlayerMenuUtility(player)).open();
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
            case "§8[§7Lvl. 1§8] §fBee":
                player.getInventory().addItem(BEE_PET_COMMON.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
            case "§6Bonemerang":
                player.getInventory().addItem(BONE_BOOMERANG.get());
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10F, 2);
                break;
            default:
                break;
        }
    }

    @Override
    public void setItems() {
        Player player = playerMenuUtility.getOwner();
        fillBottom();
        ItemStack close = makeItem(Material.BARRIER, "§cClose", 1, 0, "§7Close the menu.");
        ItemStack search = makeItem(Material.SIGN, "§aSearch items", 1, 0, "§7Find the item of", "§7your choice by searching", "", "§aClick to search!");
        ItemStack removeSearch = makeItem(Material.ENDER_PEARL, "§cReset Search", 1, 0, "§7Reset your current search", "§7by clicking this item!", "", "§eClick to reset!");
        inventory.setItem(48, removeSearch);
        inventory.setItem(49, close);
        inventory.setItem(50, search);

        if (Maps.searching.containsKey(playerMenuUtility.getOwner().getUniqueId())) {
            for (int slots = 0; slots < 45; slots++) {
                ItemStack item = inventory.getItem(slots);
                if (item != null) {
                    String name = ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase());
                    String searchObj = ChatColor.stripColor(Maps.searching.get(player.getUniqueId()).toLowerCase());
                    player.sendMessage(name);
                    player.sendMessage(searchObj);

                    if (name.contains(searchObj)) {
                        inventory.addItem(item);
                    }
                }
            }
            return;
        }

        inventory.addItem(GRAPPLING_HOOK.get(), HYPERION.get(), RADIANT_POWER_ORB.get(), MANAFLUX_POWER_ORB.get(), OVERFLUX_POWER_ORB.get(), PLASMAFLUX_POWER_ORB.get(), BEE_PET_COMMON.get(), BONE_BOOMERANG.get());
    }

    private void search() {
        SignGUI signGUI = new SignGUI(playerMenuUtility.getOwner());
        signGUI.start();
    }
}
