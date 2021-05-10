package net.maploop.items.gui;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class RainbowGUI extends GUI {
    public RainbowGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "RAINBOW!";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void setItems() {
        final DyeColor[] RAINBOW = DyeColor.values();
        final ChatColor[] chatColors = ChatColor.values();
        for(int i = 0; i < 36; ++i) {
            DyeColor color = RAINBOW[i % RAINBOW.length];
            ChatColor chatColor = chatColors[i % chatColors.length];
            inventory.addItem(makeItem(Material.WOOL, chatColor + UUID.randomUUID().toString(), 1, color.getData()));
        }
    }
}
