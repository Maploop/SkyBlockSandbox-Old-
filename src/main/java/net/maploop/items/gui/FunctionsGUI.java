package net.maploop.items.gui;

import net.maploop.items.util.IUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FunctionsGUI extends GUI {
    private final int index;

    public FunctionsGUI(PlayerMenuUtility playerMenuUtility, int index) {
        super(playerMenuUtility);
        this.index = index;
    }

    @Override
    public String getTitle() {
        return "Item Functions";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case ARROW:
                new AbilityCreatorGUI(new PlayerMenuUtility(player), index).open();
                break;
            case BOOK_AND_QUILL:
                player.sendMessage(IUtil.colorize("&cThis feature is currently disabled."));
                break;
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(31, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create Ability #" + index));
        inventory.setItem(8, makeItem(Material.BOOK_AND_QUILL, "§aAdd Function", 1, 0, "§7Add a function to your item!"));
    }
}
