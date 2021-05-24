package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUIRevamped;
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

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.NAME_TAG, "Name input", 1, 0));
                player.setItemOnCursor(null);
                gui.open();
                break;
            }
            case WATCH: {
                SignGUIRevamped.openSignEditor(player, new String[] {"", "^^^^^^", "Enter your", "cooldown!"}, e -> {
                    if(ItemUtilities.isInteger(e.getSignText()[0])) {
                        if(Integer.parseInt(e.getSignText()[0]) > 1000) {
                            player.sendMessage("§cThe cooldown cannot be more than 1,000!");
                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                            return;
                        }
                        player.setItemInHand(AbilityData.setData(EnumAbilityData.COOLDOWN, player.getItemInHand(), e.getSignText()[0], index));
                        Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                    } else {
                        player.sendMessage("§cThat's not a valid number!");
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                        Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                    }
                });
                break;
            }
            case HOPPER: {
                new BaseAbilitiesGUI(new PlayerMenuUtility(player), index).open();
                break;
            }
            case EYE_OF_ENDER: {
                SignGUIRevamped.openSignEditor(player, new String[] {"", "^^^^^^", "Enter your", "mana cost!"}, e -> {
                    if(ItemUtilities.isInteger(e.getSignText()[0])) {
                        if(Integer.parseInt(e.getSignText()[0]) > 500) {
                            player.sendMessage("§cThe mana cost cannot be more than 500!");
                            Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                            return;
                        }
                        player.setItemInHand(AbilityData.setData(EnumAbilityData.MANA_COST, player.getItemInHand(), e.getSignText()[0], index));
                        Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                    } else {
                        player.sendMessage("§cThat's not a valid number!");
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                        Bukkit.getScheduler().runTaskLater(Items.getInstance(), super::open, 2);
                    }
                });
                break;
            }
            case COMMAND: {
                new FunctionsGUI(new PlayerMenuUtility(player), index).open();
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
                IUtil.colorize("&7Set the cooldown of your ability!\n\n§7Maximum: &a1,000s\n\n&eClick to set!")));

        inventory.setItem(23, makeItem(Material.HOPPER, "§aSet base ability", 1, 0, IUtil.colorize(
                "&7Set the base ability if your item\n" +
                        "&7the base ability is the origin of\n" +
                        "&7your item's ability.\n\n" +
                        "&eClick to set!"
        )));

        inventory.setItem(22, makeItem(Material.EYE_OF_ENDER, "§aSet item Mana cost", 1, 0, IUtil.colorize(
                "&7Set the amount of&b intelligence\n&7your ability costs to use!\n\n&7Maximum: &a500\n\n&eClick to set!"
        )));

        inventory.setItem(31, makeItem(Material.COMMAND, "§aFunctions", 1, 0, "§7You can add functions to\n§7your item to make it weirder!\n§7You can add up to §a3\n§7functions to your items.\n\n§eClick to view!"));
    }
}
