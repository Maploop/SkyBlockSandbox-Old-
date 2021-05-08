package net.maploop.items.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedGUI extends GUI {
    public PaginatedGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }
    public PaginatedGUI(PlayerMenuUtility playerMenuUtility, ItemStack itemStack) {
        super(playerMenuUtility, itemStack);
    }

    protected int page = 0;
    protected int maxItemsPerPage = 45;
    protected int index = 0;

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }


}
