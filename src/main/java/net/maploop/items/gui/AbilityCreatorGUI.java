package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.data.AbilityData;
import net.maploop.items.data.EnumAbilityData;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityCreatorGUI extends GUI {
    private final int index;

    public AbilityCreatorGUI(PlayerMenuUtility playerMenuUtility, int index) {
        super(playerMenuUtility);
        this.index = index;
    }

    @Override
    public String getTitle() {
        return "Create Ability #" + index;
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        switch (event.getCurrentItem().getType()) {
            case ARROW:
                new AbilityEditorGUI(playerMenuUtility).open();
                break;
            case NAME_TAG: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(event.getSlot().equals(AnvilGUI.AnvilSlot.INPUT_LEFT)) {
                            event.setWillDestroy(false);
                            event.setWillClose(false);
                            return;
                        }
                        if(event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                            player.setItemInHand(AbilityData.setData(EnumAbilityData.NAME, player.getItemInHand(), event.getName(), index));

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    new AbilityCreatorGUI(playerMenuUtility, index).open();
                                }
                            }.runTaskLater(Items.getInstance(), 2);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.NAME_TAG, "Enter name", 1, 0));
                player.setItemOnCursor(null);
                gui.open();
                break;
            }
            case WATCH: {
                AnvilGUI gui = new AnvilGUI(player, e -> {
                    if(e.getSlot().equals(AnvilGUI.AnvilSlot.INPUT_LEFT)) {
                        e.setWillDestroy(false);
                        e.setWillClose(false);
                        return;
                    }
                    if(e.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                        if(ItemUtilities.isInteger(e.getName())) {
                            player.setItemInHand(AbilityData.setData(EnumAbilityData.COOLDOWN, player.getItemInHand(), e.getName(), index));
                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                        } else {
                            player.sendMessage("§cThat's not a valid number!");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                        }
                    }
                });
                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter integer", 1, 0));
                gui.open();
                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(49, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Edit Item Ability"));

        inventory.setItem(13, makeItem(Material.NAME_TAG, "§aSet ability name", 1, 0,
                IUtil.colorize("&7Set the name of your ability!\n\n&cNOTICE: Inappropriate ability names\n&cwill result in a warn/ban.\n\n&eClick to set name!")));

        inventory.setItem(21, makeItem(Material.WATCH, "§aSet ability cooldown", 1, 0,
                IUtil.colorize("&7Set the cooldown of your ability!\n\nMaximum: &a1,000s\n\n&eClick to set!")));
    }
}
