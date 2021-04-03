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
    public StatsEditorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public static Set<Player> healthChange = new HashSet<>();

    @Override
    public String getTitle() {
        return "Edit item stats";
    }

    @Override
    public int getSize() {
        return 45;
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
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(31, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an Item"));

        inventory.setItem(11, makeItem(Material.GOLDEN_APPLE, "§aEdit Health Attribute", 1, 0, IUtil.colorize("&7Edit the amount of &c❤ Health\n&7the item in your hand\n&7will give players when worn\n&7or held!\n\n&eClick to set!")));
    }
}
