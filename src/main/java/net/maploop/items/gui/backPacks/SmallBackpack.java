package net.maploop.items.gui.backPacks;

import net.maploop.items.data.BackpackData;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SmallBackpack extends GUI {
    public SmallBackpack(PlayerMenuUtility playerMenuUtility, String uuid, ItemStack item) {
        super(playerMenuUtility, uuid, item);
    }

    @Override
    public String getTitle() {
        return "Small BackPack";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {

    }

    @Override
    public void setItems() {
        Player player = playerMenuUtility.getViewingPlayer();
        inventory.setContents(BackpackData.getData().get(uuid));
        BackpackData.inv.put(player.getUniqueId(), itemStack);
        return;
    }
}
