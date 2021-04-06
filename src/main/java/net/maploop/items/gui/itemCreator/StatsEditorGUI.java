package net.maploop.items.gui.itemCreator;

import net.maploop.items.api.SignGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.util.IUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashSet;
import java.util.Set;

public class StatsEditorGUI extends GUI {
    /*
    TODO
      - Add mana, defense, damage, and strength editing to the GUI.
     */

    public StatsEditorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public static Set<Player> healthChange = new HashSet<>();
    public static Set<Player> intelligenceChange = new HashSet<>();
    public static Set<Player> defenseChange = new HashSet<>();
    public static Set<Player> strengthChange = new HashSet<>();

    @Override
    public String getTitle() {
        return "Edit item stats";
    }

    @Override
    public int getSize() {
        return 9 * 6;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case ARROW: {
                new ItemCreatorGUI(playerMenuUtility).open();
                break;
            }
            case GOLDEN_APPLE: {
                String[] t = new String[] {"", "^^^^^^", "Set health", "amount."};
                SignGUI.openSignEditor(player, t);
                healthChange.add(player);
                break;
            }
            case EYE_OF_ENDER: {
                String[] t = new String[] {"", "^^^^^^", "Enter intelligence", "amount."};
                SignGUI.openSignEditor(player, t);
                intelligenceChange.add(player);
                break;
            }
            case IRON_CHESTPLATE: {
                String[] t = new String[] {"", "^^^^^^", "Enter defense", "amount."};
                SignGUI.openSignEditor(player, t);
                defenseChange.add(player);
                break;
            }
            case IRON_SWORD: {
                String[] t = new String[] {"", "^^^^^^", "Enter strength", "amount."};
                SignGUI.openSignEditor(player, t);
                strengthChange.add(player);
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(49, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an Item"));

        inventory.setItem(13, makeItem(Material.EYE_OF_ENDER, "§aSet Intelligence", 1, 0, IUtil.colorize("&7Edit the amount of &b✎ Intelligence\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(15, makeItem(Material.IRON_CHESTPLATE, "§aSet Defense", 1, 0, IUtil.colorize("&7Edit the amount of &a❈ Defense\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(31, makeItem(Material.IRON_SWORD, "§aSet Strength", 1, 0, IUtil.colorize("&7Edit the amount of &c❁ Strength\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(11, makeItem(Material.GOLDEN_APPLE, "§aSet Health", 1, 0, IUtil.colorize("&7Edit the amount of &c❤ Health\n&7your item has!\n\n&eClick to set!")));
    }
}
