package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.data.DataHandler;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.util.DUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SkyblockMenuGUI extends GUI {
    public SkyblockMenuGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Skyblock Menu";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 49: {
                player.closeInventory();
                break;
            }
            default: {
                event.setCancelled(true);
                break;
            }
            case 24: {
                player.openInventory(player.getEnderChest());
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1f, 0);
                break;
            }
            case 13: {
                new ProfileGUI(playerMenuUtility).open();
                break;
            }
            case 50: {
                new SettingsGUI(playerMenuUtility).open();
                break;
            }
            case 0: {
                if(player.hasPermission("Items.admin")) {
                    new ItemsGUI(new PlayerMenuUtility(player)).open();
                    break;
                } else {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @Override
    public void setItems() {
        Player pLayer = playerMenuUtility.getOwner();
        DataHandler handler = new DataHandler(pLayer);

        setFilter();
        ItemStack close = makeItem(Material.BARRIER, "§cClose", 1, 0);
        inventory.setItem(49, close);

        ItemStack admin = makeItem(Material.COMMAND, "§aItems", 1, 0, "§7Only admins are able to\n§7see this item in their\n§7Skyblock GUI!\n§7You can access all the items\n§7available in this menu.\n \n§eClick to view!");

        if(Items.getInstance().getConfig().getBoolean("mysql.use-mysql")) {
            SQLGetter getter = new SQLGetter(pLayer, Items.getInstance());
            ItemStack profile = makeSkullItem("§aYour Skyblock Profile", pLayer.getName(), 1,
                    "§c❤ Health §f" +
                            getter.getMaxHealth() + " HP\n§a❈ Defense §f" +
                            getter.getDefense() + "\n§c❁ Strength §f" +
                            getter.getStrength() +"\n§9☣ Crit Chance §f" +
                            getter.getCritChance() + "%\n§9☠ Crit Damage §f" +
                            getter.getCritDamage() + "%\n§b✎ Intelligence §f" +
                            getter.getMaxMana() + "\n \n§eClick to view your profile!");
            inventory.setItem(13, profile);
        } else {
            ItemStack profile = makeSkullItem("§aYour Skyblock Profile", pLayer.getName(), 1,
                    "§c❤ Health §f" +
                            handler.getMaxHealth() + " HP\n§a❈ Defense §f" + handler.getDefense() +
                            "\n§c❁ Strength §f" + handler.getStrength() +"\n§9☣ Crit Chance §f" + handler.getCritChance() +
                            "%\n§9☠ Crit Damage §f" + handler.getCritDamage() +
                            "%\n§b✎ Intelligence §f" + handler.getMaxMana() +
                            "\n \n§eClick to view your profile!");
            inventory.setItem(13, profile);
        }

        ItemStack trades = makeItem(Material.EMERALD, "§cTrades", 1, 0, "§cThis feature is disabled\n§cin the Dungeon simulator\n§cserver!");
        inventory.setItem(22, trades);

        ItemStack recipeBook = makeItem(Material.BOOK, "§cRecipe Book", 1, 0, "§cThis feature is disabled\n§cin the Dungeon simulator\n§cserver!");
        ItemStack Collection = makeItem(Material.PAINTING, "§cCollection", 1, 0, "§cThis feature is disabled\n§cin the Dungeon simulator\n§cserver!");
        ItemStack QuestLog = makeItem(Material.BOOK_AND_QUILL, "§cQuest Log", 1, 0, "§cThis feature is disabled\n§cin the Dungeon simulator\n§cserver!");
        ItemStack crafting = makeItem(Material.WORKBENCH, "§cCrafting Table", 1, 0, "§cThis feature is disabled\n§cin the Dungeon simulator\n§cserver!");

        ItemStack enderChest = makeItem(Material.ENDER_CHEST, DUtil.colorize("&aEnderchest"), 1, 0, DUtil.colorize("&7A special container where you\n&7can store any item you want!\n&7even backpacks!\n \n&eClick to open!"));

        inventory.setItem(50, makeItem(Material.REDSTONE_TORCH_ON, "§aSettings", 1, 0, DUtil.colorize("&7View and edit your Skyblock\n&7settings.\n\n&eClick to view!")));
        inventory.setItem(21, recipeBook);
        inventory.setItem(20, Collection);
        inventory.setItem(23, QuestLog);
        inventory.setItem(24, enderChest);
        inventory.setItem(31, crafting);

        if(pLayer.hasPermission("dungeons.admin")) {
            inventory.setItem(0, admin);
        }
    }
}