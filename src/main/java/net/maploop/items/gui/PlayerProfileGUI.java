package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.DecimalFormat;

public class PlayerProfileGUI extends GUI {
    private final Player player;
    public PlayerProfileGUI(PlayerMenuUtility playerMenuUtility, Player player) {
        super(playerMenuUtility);
        this.player = player;
    }

    @Override
    public String getTitle() {
        return player.getName() + "'s Profile";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player1 = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 49: {
                player1.closeInventory();
                break;
            }
        }
    }

    @Override
    public void setItems() {
        // 22
        DecimalFormat format = new DecimalFormat("#,###");
        DataHandler handler = new DataHandler(player);
        setFilter();
        inventory.setItem(49, makeItem(Material.BARRIER, "§cClose", 1, 0));

        if(Items.getInstance().getConfig().getBoolean("mysql.use-mysql")) {
            SQLGetter getter = new SQLGetter(player, Items.getInstance());
            inventory.setItem(22, makeSkullItem(player.getDisplayName(), player.getName(), 1,
                    IUtil.colorize("&c ❤ Health &f" + Math.round(Float.parseFloat(format.format(getter.getMaxHealth()))) +
                            " HP\n&c ❁ Strength &f" + Math.round(Float.parseFloat(format.format(getter.getStrength()))) +
                            "\n&a ❈ Defense &f" + Math.round(Float.parseFloat(format.format(getter.getDefense()))) +
                            "\n&9 ☣ Crit Chance &f100%\n&9 ☠ Crit Damage &f" + Math.round(Float.parseFloat(format.format(getter.getCritDamage()))) +
                            "\n&b ✎ Intelligence &f" + Math.round(Float.parseFloat(format.format(getter.getMaxMana()))))));
        } else {
            inventory.setItem(22, makeSkullItem(player.getDisplayName(), player.getName(), 1,
                    IUtil.colorize("&c ❤ Health &f" + Math.round(Float.parseFloat(format.format(handler.getMaxHealth()))) +
                            " HP\n&c ❁ Strength &f" + Math.round(Float.parseFloat(format.format(handler.getStrength()))) +
                            "\n&a ❈ Defense &f" + Math.round(Float.parseFloat(format.format(handler.getDefense()))) +
                            "\n&9 ☣ Crit Chance &f100%\n&9 ☠ Crit Damage &f" + Math.round(Float.parseFloat(format.format(handler.getCritDamage()))) +
                            "\n&b ✎ Intelligence &f" + Math.round(Float.parseFloat(format.format(handler.getMaxMana()))))));
        }

        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                    inventory.setItem(1, makeItem(Material.STAINED_GLASS_PANE, "§eHeld Item", 1, 0, "§cEmpty!"));
                } else {
                    inventory.setItem(1, player.getItemInHand());
                }

                if(player.getEquipment().getHelmet() == null || player.getEquipment().getHelmet().getType() == Material.AIR) {
                    inventory.setItem(10, makeItem(Material.STAINED_GLASS_PANE, "§eHelmet", 1, 0, "§cEmpty!"));
                } else {
                    inventory.setItem(10, player.getEquipment().getHelmet());
                }

                if(player.getEquipment().getChestplate() == null || player.getEquipment().getChestplate().getType() == Material.AIR) {
                    inventory.setItem(19, makeItem(Material.STAINED_GLASS_PANE, "§eChestplate", 1, 0, "§cEmpty!"));
                } else {
                    inventory.setItem(19, player.getEquipment().getChestplate());
                }

                if(player.getEquipment().getLeggings() == null || player.getEquipment().getLeggings().getType() == Material.AIR) {
                    inventory.setItem(28, makeItem(Material.STAINED_GLASS_PANE, "§eLeggings", 1, 0, "§cEmpty!"));
                } else {
                    inventory.setItem(28, player.getEquipment().getLeggings());
                }

                if(player.getEquipment().getBoots() == null || player.getEquipment().getBoots().getType() == Material.AIR) {
                    inventory.setItem(37, makeItem(Material.STAINED_GLASS_PANE, "§eBoots", 1, 0, "§cEmpty!"));
                } else {
                    inventory.setItem(37, player.getEquipment().getBoots());
                }
            }
        }, 0, 1);
    }
}
