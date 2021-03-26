package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.DUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ProfileGUI extends GUI {
    public ProfileGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Your Skyblock Profile";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 49: {
                event.getWhoClicked().closeInventory();
                break;
            }
            case 48: {
                new SkyblockMenuGUI(playerMenuUtility).open();
            }
        }
    }

    @Override
    public void setItems() {
        Player player = playerMenuUtility.getOwner();
        DataHandler handler = new DataHandler(player);
        setFilter();
        inventory.setItem(49, makeItem(Material.BARRIER, DUtil.colorize("&cClose"), 1, 0));
        inventory.setItem(48, makeItem(Material.ARROW, DUtil.colorize("&aGo Back"), 1, 0, ChatColor.GRAY + "To Skyblock GUI"));

        inventory.setItem(4,  makeSkullItem("§aYour Skyblock Profile", player.getName(), 1,
                "§c❤ Health §f" + handler.getMaxHealth() + " HP\n§a❈ Defense §f" + handler.getDefense() + "\n§c❁ Strength §f" + handler.getStrength() +"\n§9☣ Crit Chance §f" + handler.getCritChance() + "%\n§9☠ Crit Damage §f" + handler.getCritDamage() + "%\n§b✎ Intelligence §f" + handler.getMaxMana()));

        if(Items.getInstance().getConfig().getBoolean("mysql.use-mysql")) {
            SQLGetter getter = new SQLGetter(player, Items.getInstance());
            inventory.setItem(19, makeItem(Material.GOLDEN_APPLE, "§c❤ Health §f" + Math.round(getter.getMaxHealth()) + " HP", 1, 0,
                    DUtil.colorize("&7Health is your total maximum\n&7health. Your natural\n&7regeneration gives &a3.7 HP\n&7every &a2s&7.\n\n&7Base Health: &a" + getter.getMaxHealth() +
                            "\n&8&o Increase your base Health by\n&8&o leveling your Farming and\n&8&o Fishing Skills and contributing\n&8&o to the &dFairy &8&oin the\n&2 Wilderness&8&o." +
                            "")));
        } else {
            inventory.setItem(19, makeItem(Material.GOLDEN_APPLE, "§c❤ Health §f" + Math.round(handler.getMaxHealth()) + " HP", 1, 0,
                    DUtil.colorize("&7Health is your total maximum\n&7health. Your natural\n&7regeneration gives &a3.7 HP\n&7every &a2s&7.\n\n&7Base Health: &a" + handler.getMaxHealth() +
                            "\n&8&o Increase your base Health by\n&8&o leveling your Farming and\n&8&o Fishing Skills and contributing\n&8&o to the &dFairy &8&oin the\n&2 Wilderness&8&o." +
                            "")));
        }
    }
}
