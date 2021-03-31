package net.maploop.items.gui.itemCreator;

import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StatsEditorGUI extends GUI {
    public StatsEditorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

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
    }

    @Override
    public void setItems() {
        setFilter();
    }
}
