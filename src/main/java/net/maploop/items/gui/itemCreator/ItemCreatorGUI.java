package net.maploop.items.gui.itemCreator;

import net.maploop.items.Items;
import net.maploop.items.gui.AnvilGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.util.IUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemCreatorGUI extends GUI {
    public ItemCreatorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Create an item";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 31: {
                player.closeInventory();
                break;
            }
            case 4: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(event.getSlot().equals(AnvilGUI.AnvilSlot.INPUT_LEFT)) {
                            event.setWillDestroy(false);
                            event.setWillClose(false);
                            return;
                        }
                        if(event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                            ItemMeta meta = player.getItemInHand().getItemMeta();
                            meta.setDisplayName(IUtil.colorize(event.getName()));
                            player.getItemInHand().setItemMeta(meta);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.openInventory(inventory);
                                }
                            }.runTaskLater(Items.getInstance(), 2);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.NAME_TAG, "Enter item name", 1, 0));
                gui.open();
                break;
            }
            case 13: {
                new ItemLoreGUI(new PlayerMenuUtility(player)).open();
                break;
            }
            case 14: {
                new StatsEditorGUI(playerMenuUtility).open();
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        Player player = playerMenuUtility.getOwner();

        inventory.setItem(31, makeItem(Material.BARRIER, "§cClose", 1, 0));
        inventory.setItem(4, makeItem(Material.NAME_TAG, "§aRename Item", 1, 0,
                IUtil.colorize("&7Rename the item you have\n&7in your hand!\n\n&eClick to rename!")));

        inventory.setItem(13, makeItem(Material.PAPER, "§aEdit Item Lore", 1, 0, IUtil.colorize("&7Edit the lore of the item you\n&7have in your hand!\n\n&eClick to edit!")));
        inventory.setItem(14, makeItem(Material.GOLDEN_APPLE, "§aEdit Item Stats", 1, 0, IUtil.colorize("&7Edit the stats the item has!\n&7Including Defense, Health, and Intelligence!\n\n&eClick to edit!")));
    }
}
