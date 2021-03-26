package net.maploop.items.gui.itemsMenu;

import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CategoriesGUI extends GUI {
    public CategoriesGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Item Categories";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void setItems() {

    }
}
