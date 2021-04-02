package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SBAnvilGUI extends GUI {
    public SBAnvilGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    boolean canReforge = false;

    ItemStack itemToUpgrade1 = makeItem(Material.STAINED_GLASS_PANE, "§6Item To Upgrade", 1, 14, IUtil.colorize("&7The item you want to\n&7upgrade should be placed\n&7in the slot on this side."));
    ItemStack itemToUpgrade2 = makeItem(Material.STAINED_GLASS_PANE, "§6Item To Upgrade", 1, 5, IUtil.colorize("&7The item you want to\n&7upgrade should be placed\n&7in the slot on this side."));
    ItemStack itemToSacrifice1 = makeItem(Material.STAINED_GLASS_PANE, "§6Item to Sacrifice", 1, 14, IUtil.colorize("&7The item you are\n&7sacrificing in order to\n&7upgrade the item on the left should\n&7be placed on this side."));
    ItemStack itemToSacrifice2 = makeItem(Material.STAINED_GLASS_PANE, "§6Item to Sacrifice", 1, 5, IUtil.colorize("&7The item you are\n&7sacrificing in order to\n&7upgrade the item on the left should\n&7be placed on this side."));
    ItemStack combineButton = makeItem(Material.ANVIL, "§aCombine Items", 1, 0, IUtil.colorize("Combine the items in the"), IUtil.colorize("slots to the left and the right"), IUtil.colorize("below."));

    ItemStack combineItems = makeItem(Material.ANVIL, "§aCombine Items", 1, 0, IUtil.colorize("&7Combine items in the\n&7slots to the left and right\n&7below."));

    @Override
    public String getTitle() {
        return "Anvil";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        ItemStack a = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case BARRIER: {
                if(a.getItemMeta().getDisplayName().contains("§cClose")) {
                    player.closeInventory();
                    break;
                }
                if(a.getItemMeta().getDisplayName().contains("§cAnvil")) {
                    event.setCancelled(true);
                }
            }
            case STAINED_GLASS_PANE: {
                event.setCancelled(true);
                break;
            }
            case ANVIL: {
                event.setCancelled(true);
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();

        inventory.setItem(13, makeItem(Material.BARRIER, "§cAnvil", 1, 0,
                IUtil.colorize("&7Place a target item in the left\n&7slot and a sacrifice item in the\n&7right slot to combine\n&7Enchantments!")));

        startRunnable(inventory);
    }

    private void setItemToUpgrade1(Inventory inv) {
        inv.setItem(11, itemToUpgrade1);
        inv.setItem(12, itemToUpgrade1);
        inv.setItem(20, itemToUpgrade1);
    }

    private void startRunnable(Inventory inv) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (inv.getItem(29) != null && ItemUtilities.isSBItem(inv.getItem(29))) {
                    ItemStack itemToUpgradeGreen = itemToUpgrade1.clone();
                    itemToUpgradeGreen.setDurability((short) 5);
                }
            }
        }.runTaskTimer(Items.getInstance(), 0, 2);
    }

    private void setItemToUpgrade2(Inventory inv) {
        inv.setItem(11, itemToUpgrade2);
        inv.setItem(12, itemToUpgrade2);
        inv.setItem(20, itemToUpgrade2);
    }

    private void setItemToSacrifice1(Inventory inv) {
        inv.setItem(14, itemToSacrifice1);
        inv.setItem(15, itemToSacrifice1);
        inv.setItem(24, itemToSacrifice1);
    }

    private void setItemToSacrifice2(Inventory inv) {
        inv.setItem(14, itemToSacrifice2);
        inv.setItem(15, itemToSacrifice2);
        inv.setItem(24, itemToSacrifice2);
    }
}
