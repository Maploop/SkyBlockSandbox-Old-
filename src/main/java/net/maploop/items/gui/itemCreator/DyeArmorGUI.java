package net.maploop.items.gui.itemCreator;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUI;
import net.maploop.items.gui.PaginatedGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.item.SBItems;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DyeArmorGUI extends PaginatedGUI {
    public static Set<Player> searching = new HashSet<>();
    public static Map<Player, String> search = new HashMap<>();

    public DyeArmorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        int i = page + 1;
        int i1 = index + 1;
        return "Select an Item (" + i + ")";
    }

    @Override
    public int getMaxItemsPerPage() {
        return 28;
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        if(event.getCurrentItem().getType() == Material.AIR) return;
        if(event.getCurrentItem() == null) return;

        List<ItemStack> items = SBItems.getItems();
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case BARRIER: {
                player.closeInventory();
                break;
            }
            case STAINED_GLASS_PANE: {
                event.setCancelled(true);
                break;
            }
        }
        switch (event.getSlot()) {

        }
    }

    @Override
    public void setItems() {
        fillBorder();

        List<ItemStack> items = new ArrayList<>(SBItems.getItems());



    }
}
