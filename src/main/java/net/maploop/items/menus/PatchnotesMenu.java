package net.maploop.items.menus;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PatchnotesMenu extends Menu{
    public PatchnotesMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Skybox Patch notes";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {

    }

    @Override
    public void setItems() {
        fillBorder();
        ItemStack one_zero = makeItem(Material.BOOK, "§[18/02/2021] §aSkybox Release 1.0", 1, 0);
    }
}
