package net.maploop.items.gui.wardobes;

import net.maploop.items.Items;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.BukkitSerialization;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WardobeGUI extends GUIManager {
    public WardobeGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Wardrobe";
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        Player player = playerMenuUtility.getOwner();
        ArrayList<Integer> helmet = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            helmet.add(i);
        }
        ArrayList<Integer> chestplate = new ArrayList<>();
        for (int i = 9; i < 18; i++) {
            chestplate.add(i);
        }
        ArrayList<Integer> leggings = new ArrayList<>();
        for (int i = 18; i < 27; i++) {
            leggings.add(i);
        }
        ArrayList<Integer> boots = new ArrayList<>();
        for (int i = 27; i < 36; i++) {
            boots.add(i);
        }
        ArrayList<Integer> equip = new ArrayList<>();
        for (int i = 36; i < 45; i++) {
            equip.add(i);
        }
        if(event.getClickedInventory().equals(inventory)) {
            event.setCancelled(true);
            for (int slot : helmet) {
                Material material = event.getCursor().getType();
                if (event.getSlot() == slot) {
                    if (event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                        if (material.equals(Material.GOLD_HELMET) || material.equals(Material.IRON_HELMET) || material.equals(Material.DIAMOND_HELMET) || material.equals(Material.CHAINMAIL_HELMET) || material.equals(Material.SKULL_ITEM) || material.equals(Material.SKULL) || material.equals(Material.LEATHER_HELMET)) {
                            inventory.setItem(slot, player.getItemOnCursor());
                            player.setItemOnCursor(null);
                        }
                    }else {
                        if(event.getClick().equals(ClickType.RIGHT) || event.getClick().equals(ClickType.LEFT)) {
                            if (event.getCursor().getType().equals(Material.AIR)) {
                                player.setItemOnCursor(event.getCurrentItem());
                                inventory.setItem(slot, getSlot(slot + 1, slot + 1, 1));
                            } else {
                                if (material.equals(Material.GOLD_HELMET) || material.equals(Material.IRON_HELMET) || material.equals(Material.DIAMOND_HELMET) || material.equals(Material.CHAINMAIL_HELMET) || material.equals(Material.SKULL_ITEM) || material.equals(Material.SKULL) || material.equals(Material.LEATHER_HELMET)) {
                                    ItemStack item = player.getItemOnCursor();
                                    ItemStack itemSet = event.getCurrentItem();
                                    inventory.setItem(slot, item);
                                    player.setItemOnCursor(itemSet);
                                } else {
                                    player.getInventory().addItem(event.getCurrentItem());
                                    inventory.setItem(slot, getSlot(slot + 1, slot + 1, 1));
                                }
                            }
                        } else {
                            player.getInventory().addItem(event.getCurrentItem());
                            inventory.setItem(slot, getSlot(slot + 1, slot + 1, 1));
                        }
                    }
                }
            }

            for (int slot : equip) {
                if (event.getSlot() == slot) {
                    if (event.getCurrentItem().equals(getEmpty(slot - 35))) {
                        boolean helm = false;
                        for (int slot2 : equip) {
                            if (inventory.getItem(slot2).equals(getEquiped(slot2 - 35))) {
                                if(player.getInventory().getHelmet() != null) {
                                    Material material = inventory.getItem(slot2 - 36).getType();
                                    if (material.equals(Material.GOLD_HELMET) || material.equals(Material.IRON_HELMET) || material.equals(Material.DIAMOND_HELMET) || material.equals(Material.CHAINMAIL_HELMET) || material.equals(Material.SKULL_ITEM) || material.equals(Material.SKULL) || material.equals(Material.LEATHER_HELMET)) {
                                        helm = true;
                                    }
                                }
                                inventory.setItem(slot2, getEmpty(slot2 - 35));
                            }
                        }
                        inventory.setItem(slot, getEquiped(slot - 35));
                        if (!inventory.getItem(slot - 36).getType().equals(Material.STAINED_GLASS_PANE)) {
                            if(player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType().equals(Material.AIR)) {
                                player.getInventory().setHelmet(inventory.getItem(slot - 36));
                            } else {
                                if(!helm) {
                                    player.getInventory().addItem(player.getInventory().getHelmet());
                                    player.getInventory().setHelmet(inventory.getItem(slot - 36));
                                    helm = false;
                                }
                            }
                        } else {
                            if(player.getInventory().getHelmet() != null) {
                                Material material = player.getInventory().getHelmet().getType();
                                if (material.equals(Material.GOLD_HELMET) || material.equals(Material.IRON_HELMET) || material.equals(Material.DIAMOND_HELMET) || material.equals(Material.CHAINMAIL_HELMET) || material.equals(Material.SKULL_ITEM) || material.equals(Material.SKULL) || material.equals(Material.LEATHER_HELMET)) {
                                    if(!helm) {
                                        inventory.setItem(slot - 36, player.getInventory().getHelmet());
                                        helm = false;
                                    }
                                }
                            } else {
                                player.getInventory().setHelmet(null);
                            }
                        }
                        if (!inventory.getItem(slot - 27).getType().equals(Material.STAINED_GLASS_PANE)) {
                            player.getInventory().addItem(player.getInventory().getChestplate());
                            player.getInventory().setHelmet(inventory.getItem(slot - 27));
                        }
                        if (!inventory.getItem(slot - 18).getType().equals(Material.STAINED_GLASS_PANE)) {
                            player.getInventory().addItem(player.getInventory().getLeggings());
                            player.getInventory().setHelmet(inventory.getItem(slot - 18));
                        }
                        if (!inventory.getItem(slot - 9).getType().equals(Material.STAINED_GLASS_PANE)) {
                            player.getInventory().addItem(player.getInventory().getBoots());
                            player.getInventory().setHelmet(inventory.getItem(slot - 9));
                        }
                    } else if (event.getCurrentItem().equals(getEquiped(slot - 35))) {
                        inventory.setItem(slot, getEmpty(slot - 35));
                        playerMenuUtility.getOwner().getInventory().setHelmet(null);
                        playerMenuUtility.getOwner().getInventory().setChestplate(null);
                        playerMenuUtility.getOwner().getInventory().setLeggings(null);
                        playerMenuUtility.getOwner().getInventory().setBoots(null);
                    }
                }
            }
        }
    }

    @Override
    public void setItems() {
        ArrayList<Integer> helmet = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            helmet.add(i);
        }
        ArrayList<Integer> chestplate = new ArrayList<>();
        for (int i = 9; i < 18; i++) {
            chestplate.add(i);
        }
        ArrayList<Integer> leggings = new ArrayList<>();
        for (int i = 18; i < 27; i++) {
            leggings.add(i);
        }
        ArrayList<Integer> boots = new ArrayList<>();
        for (int i = 27; i < 36; i++) {
            boots.add(i);
        }
        ArrayList<Integer> equip = new ArrayList<>();
        for (int i = 36; i < 45; i++) {
            equip.add(i);
        }
        SQLGetter getter = new SQLGetter(playerMenuUtility.getOwner(), Items.getInstance());
        if((getter.getWardobe() == null || getter.getWardobe().equalsIgnoreCase("none"))) {
            for (int i = 0; i < inventory.getSize(); i++) {
                for(Integer helm : helmet) {
                    if(helm == i) {
                        inventory.setItem(i,getSlot(i + 1,i + 1,1));
                    }
                }
                for(Integer chest : chestplate) {
                    if(chest == i){
                        inventory.setItem(i,getSlot(i - 8,i - 8,2));
                    }
                }
                for(Integer legs : leggings) {
                    if(legs == i){
                        inventory.setItem(i,getSlot(i - 17,i - 17,3));
                    }
                }
                for(Integer boot : boots) {
                    if(boot == i){
                        inventory.setItem(i,getSlot(i - 26,i - 26,4));
                    }
                }
                for(Integer equ : equip) {
                    if(equ == i){
                        inventory.setItem(i,getEmpty(i - 35));
                    }
                }
            }
            return;
        }
        try {
            inventory.setContents(BukkitSerialization.fromBase64(getter.getWardobe()).getContents());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeMenu(InventoryCloseEvent event) {
        SQLGetter getter = new SQLGetter(playerMenuUtility.getOwner(), Items.getInstance());
        getter.setWardobe(BukkitSerialization.toBase64(inventory));
    }
}
