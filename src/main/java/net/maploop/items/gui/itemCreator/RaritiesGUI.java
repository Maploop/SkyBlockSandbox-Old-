package net.maploop.items.gui.itemCreator;

import net.maploop.items.enums.Rarity;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RaritiesGUI extends GUI {
    public RaritiesGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Set item rarity";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack item = player.getItemInHand();

        switch (event.getSlot()) {
            case 31:
                new ItemCreatorGUI(playerMenuUtility).open();
                break;
            case 10: {
                setRarity(Rarity.COMMON, item, player);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                break;
            }
            case 11: {
                setRarity(Rarity.UNCOMMON, item, player);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                break;
            }
            case 12: {
                setRarity(Rarity.RARE, item, player);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                break;
            }
            case 13: {
                setRarity(Rarity.EPIC, item, player);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                break;
            }
            case 14: {
                setRarity(Rarity.LEGENDARY, item, player);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                break;
            }
            case 15: {
                setRarity(Rarity.MYTHIC, item, player);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                break;
            }
            case 16: {
                setRarity(Rarity.SPECIAL, item, player);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(31, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an item"));

        inventory.setItem(10, makeItem(Material.STAINED_CLAY, "§fCommon", 1, 0, "§7Set your item's rarity\n§7to §fCommon§7!\n\n§eClick to set!"));
        inventory.setItem(11, makeItem(Material.STAINED_CLAY, "§aUncommon", 1, 5, "§7Set your item's rarity\n§7to §aUncommon§7!\n\n§eClick to set!"));
        inventory.setItem(12, makeItem(Material.STAINED_CLAY, "§9Rare", 1, 3, "§7Set your item's rarity\n§7to §9Rare§7!\n\n§eClick to set!"));
        inventory.setItem(13, makeItem(Material.STAINED_CLAY, "§5Epic", 1, 10, "§7Set your item's rarity\n§7to §5Epic§7!\n\n§eClick to set!"));
        inventory.setItem(14, makeItem(Material.STAINED_CLAY, "§6Legendary", 1, 4, "§7Set your item's rarity\n§7to §6Legendary§7!\n\n§eClick to set!"));
        inventory.setItem(15, makeItem(Material.STAINED_CLAY, "§dMythic", 1, 2, "§7Set your item's rarity\n§7to §dMythic§7!\n\n§eClick to set!"));
        inventory.setItem(16, makeItem(Material.STAINED_CLAY, "§cSpecial", 1, 14, "§7Set your item's rarity\n§7to §cSpecial§7!\n\n§eClick to set!"));
    }

    private void setRarity(Rarity r, ItemStack i, Player player) {
        if(ItemUtilities.hasRarity(i)) {
            ItemStack i1 = ItemUtilities.storeStringInItem(i, r.toString(), "Rarity");
            ItemMeta imeta = i1.getItemMeta();
            List<String> l = new ArrayList<>(imeta.getLore());
            l.set(l.size()-1, r.getColor() + "" + ChatColor.BOLD + r.toString());
            imeta.setDisplayName(r.getColor() + ChatColor.stripColor(imeta.getDisplayName()));
            imeta.setLore(l);
            i1.setItemMeta(imeta);
            player.setItemInHand(i1);
        } else {
            ItemStack i1 = ItemUtilities.storeStringInItem(i, r.toString(), "Rarity");
            ItemMeta imeta = i1.getItemMeta();
            List<String> l;
            if(imeta.hasLore())
                l = new ArrayList<>(imeta.getLore());
            else
                l = new ArrayList<>();

            l.add(r.getColor() + "" + ChatColor.BOLD + r.toString());
            imeta.setDisplayName(r.getColor() + ChatColor.stripColor(imeta.getDisplayName()));
            imeta.setLore(l);

            i1.setItemMeta(imeta);
            player.setItemInHand(i1);
        }
    }
}
