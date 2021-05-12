package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.data.AbilityData;
import net.maploop.items.data.EnumAbilityData;
import net.maploop.items.gui.itemCreator.ItemCreatorGUI;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityEditorGUI extends GUI {
    public AbilityEditorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Edit Item Ability";
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
                new ItemCreatorGUI(playerMenuUtility).open();
                break;
            case BOOK_AND_QUILL: {
                player.playSound(player.getLocation(), Sound.HORSE_ARMOR, 1, 1);
                if(event.getClick().equals(ClickType.RIGHT)) {
                    switch (event.getSlot()) {
                        case 11:
                            player.setItemInHand(AbilityData.removeAbility(player.getItemInHand(), 1));

                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                            break;
                        case 12:
                            player.setItemInHand(AbilityData.removeAbility(player.getItemInHand(), 2));

                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                            break;
                        case 13:
                            player.setItemInHand(AbilityData.removeAbility(player.getItemInHand(), 3));

                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                            break;
                        case 14:
                            player.setItemInHand(AbilityData.removeAbility(player.getItemInHand(), 4));

                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                            break;
                        case 15:
                            player.setItemInHand(AbilityData.removeAbility(player.getItemInHand(), 5));

                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                            break;
                        default:
                            break;
                    }
                    break;
                }
                switch (event.getSlot()) {
                    case 11:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 1).open();
                        break;
                    case 12:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 2).open();
                        break;
                    case 13:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 3).open();
                        break;
                    case 14:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 4).open();
                        break;
                    case 15:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 5).open();
                        break;
                }
                break;
            }
            case WOOD_BUTTON: {
                if(event.getClick().equals(ClickType.RIGHT)) {
                    break;
                }
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                switch (event.getSlot()) {
                    case 11:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 1).open();
                        break;
                    case 12:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 2).open();
                        break;
                    case 13:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 3).open();
                        break;
                    case 14:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 4).open();
                        break;
                    case 15:
                        new AbilityCreatorGUI(new PlayerMenuUtility(player), 5).open();
                        break;
                    default:
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void setItems() {
        Player player = playerMenuUtility.getOwner();
        ItemStack item = player.getItemInHand();

        setFilter();
        inventory.setItem(31, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an item"));

        if(!(AbilityData.hasAbility(item, 1)))
            inventory.setItem(11, makeItem(Material.WOOD_BUTTON, "§cEmpty slot", 1, 0, IUtil.colorize("&7This ability slot is empty!\n&7this means you can put a\n&7new ability in here!\n\n&7Slot index: &a1\n\n&eClick to add ability!")));
        else
            inventory.setItem(11, makeItem(Material.BOOK_AND_QUILL, "§aAbility #1", 1, 0, IUtil.colorize("&7This slot has an ability!\n\n&7Ability name: &a" + AbilityData.retrieveData(EnumAbilityData.NAME, item, 1) +
                    "\n" +
                    "&7Ability cooldown: &a" + AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, 1) + "\n" +
                    "&7Function: &a" + AbilityData.retrieveData(EnumAbilityData.FUNCTION, item, 1) + "\n" +
                    "&7Damage value: &c" + AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, 1) + "\n" +
                    "\n\n&eClick to edit!\n&bRight-Click to remove!")));

        if(!(AbilityData.hasAbility(item, 2)))
            inventory.setItem(12, makeItem(Material.WOOD_BUTTON, "§cEmpty slot", 1, 0, IUtil.colorize("&7This ability slot is empty!\n&7this means you can put a\n&7new ability in here!\n\n&7Slot index: &a2\n\n&eClick to add ability!")));
        else
            inventory.setItem(12, makeItem(Material.BOOK_AND_QUILL, "§aAbility #2", 1, 0, IUtil.colorize("&7This slot has an ability!\n\n&7Ability name: &a" + AbilityData.retrieveData(EnumAbilityData.NAME, item, 2) +
                    "\n" +
                    "&7Ability cooldown: &a" + AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, 2) + "\n" +
                    "&7Function: &a" + AbilityData.retrieveData(EnumAbilityData.FUNCTION, item, 2) + "\n" +
                    "&7Damage value: &c" + AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, 2) + "\n" +
                    "\n\n&eClick to edit!\n&bRight-Click to remove!")));

        if(player.hasPermission("items.vip")) {
            if(!(AbilityData.hasAbility(item, 3)))
                inventory.setItem(13, makeItem(Material.WOOD_BUTTON, "§cEmpty slot", 1, 0, IUtil.colorize("&7This ability slot is empty!\n&7this means you can put a\n&7new ability in here!\n\n&7Slot index: &a3\n\n&eClick to add ability!")));
            else
                inventory.setItem(13, makeItem(Material.BOOK_AND_QUILL, "§aAbility #3", 1, 0, IUtil.colorize("&7This slot has an ability!\n\n&7Ability name: &a" + AbilityData.retrieveData(EnumAbilityData.NAME, item, 3) +
                        "\n" +
                        "&7Ability cooldown: &a" + AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, 3) + "\n" +
                        "&7Function: &a" + AbilityData.retrieveData(EnumAbilityData.FUNCTION, item, 3) + "\n" +
                        "&7Damage value: &c" + AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, 3) + "\n" +
                        "\n\n&eClick to edit!\n&bRight-Click to remove!")));
        } else {
            inventory.setItem(13, makeItem(Material.BEDROCK, "§cLocked Slot", 1, 0, "§7This slot is locked for you!\n§7To unlock this ability slot\n§7you can buy a rank at our\n§7store!\n\n§cLocked!"));
        }

        if(player.hasPermission("items.mvp")) {
            if(!(AbilityData.hasAbility(item, 4)))
                inventory.setItem(14, makeItem(Material.WOOD_BUTTON, "§cEmpty slot", 1, 0, IUtil.colorize("&7This ability slot is empty!\n&7this means you can put a\n&7new ability in here!\n\n&7Slot index: &a4\n\n&eClick to add ability!")));
            else
                inventory.setItem(14, makeItem(Material.BOOK_AND_QUILL, "§aAbility #4", 1, 0, IUtil.colorize("&7This slot has an ability!\n\n&7Ability name: &a" + AbilityData.retrieveData(EnumAbilityData.NAME, item, 4) +
                        "\n" +
                        "&7Ability cooldown: &a" + AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, 4) + "\n" +
                        "&7Function: &a" + AbilityData.retrieveData(EnumAbilityData.FUNCTION, item, 4) + "\n" +
                        "&7Damage value: &c" + AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, 4) + "\n" +
                        "\n\n&eClick to edit!\n&bRight-Click to remove!")));
        } else {
            inventory.setItem(14, makeItem(Material.BEDROCK, "§cLocked Slot", 1, 0, "§7This slot is locked for you!\n§7To unlock this ability slot\n§7you can buy a rank at our\n§7store!\n\n§cLocked!"));
        }

        if(player.hasPermission("items.mvp++")) {
            if(!(AbilityData.hasAbility(item, 5)))
                inventory.setItem(15, makeItem(Material.WOOD_BUTTON, "§cEmpty slot", 1, 0, IUtil.colorize("&7This ability slot is empty!\n&7this means you can put a\n&7new ability in here!\n\n&7Slot index: &a5\n\n&eClick to add ability!")));
            else
                inventory.setItem(15, makeItem(Material.BOOK_AND_QUILL, "§aAbility #5", 1, 0, IUtil.colorize("&7This slot has an ability!\n\n&7Ability name: &a" + AbilityData.retrieveData(EnumAbilityData.NAME, item, 5) +
                        "\n" +
                        "&7Ability cooldown: &a" + AbilityData.retrieveData(EnumAbilityData.COOLDOWN, item, 5) + "\n" +
                        "&7Function: &a" + AbilityData.retrieveData(EnumAbilityData.FUNCTION, item, 5) + "\n" +
                        "&7Damage value: &c" + AbilityData.retrieveData(EnumAbilityData.DAMAGE, item, 5) + "\n" +
                        "\n\n&eClick to edit!\n&bRight-Click to remove!")));
        } else {
            inventory.setItem(15, makeItem(Material.BEDROCK, "§cLocked Slot", 1, 0, "§7This slot is locked for you!\n§7To unlock this ability slot\n§7you can buy a rank at our\n§7store!\n\n§cLocked!"));
        }
    }
}
