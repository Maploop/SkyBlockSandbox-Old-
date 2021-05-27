package net.maploop.items.gui.itemCreator;

import net.maploop.items.Items;
import net.maploop.items.gui.AbilityEditorGUI;
import net.maploop.items.gui.AnvilGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.util.IUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Stream;

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
        return (5*9);
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if(event.getClickedInventory().getType().equals(InventoryType.PLAYER)){
            return;
        }

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
            case 12: {
                if(player.getItemInHand().getItemMeta().hasDisplayName() && player.getItemInHand().getItemMeta().hasLore()) {
                    new RaritiesGUI(playerMenuUtility).open();
                    break;
                } else {
                    player.sendMessage("§cYour item must have a displayname and lore first in order to set it's rarity!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                    break;
                }
            }
            case 11: {
                if(player.getItemInHand().getItemMeta().hasDisplayName() && player.getItemInHand().getItemMeta().hasLore()) {
                    if(Stream.of(Material.STAINED_CLAY, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE, Material.INK_SACK, Material.WOOL,Material.LEATHER_LEGGINGS,Material.LEATHER_HELMET,Material.LEATHER_BOOTS,Material.LEATHER_CHESTPLATE).anyMatch(material -> player.getItemInHand().getType().equals(material))){
                        player.closeInventory();
                        player.performCommand("dye");
                        break;
                    } else {
                        player.sendMessage("§cThat isn't a dyeable item!");
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                        break;
                    }
                } else {
                    player.sendMessage("§cYour item must have a displayname and lore first in order to set it's color!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                    break;
                }
            }
            case 15: {
                if(player.hasPermission("items.admin")) {
                    new AbilityEditorGUI(new PlayerMenuUtility(player)).open();
                } else {
                    player.sendMessage("§cThis feature is coming soon!");
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                }
            }
            case 22: {
                if(player.getItemInHand().getItemMeta().hasDisplayName() && player.getItemInHand().getItemMeta().hasLore()) {
                    new TagsEditorGUI(playerMenuUtility).open();
                    break;
                } else {
                    player.sendMessage("§cYour item must have a displayname and lore first in order to set it's tags!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                    break;
                }
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        Player player = playerMenuUtility.getOwner();

        inventory.setItem(40, makeItem(Material.BARRIER, "§cClose", 1, 0));
        inventory.setItem(4, makeItem(Material.NAME_TAG, "§aRename Item", 1, 0,
                IUtil.colorize("&7Rename the item you have\n&7in your hand!\n\n&eClick to rename!")));

        inventory.setItem(13, makeItem(Material.PAPER, "§aEdit Item Lore", 1, 0, IUtil.colorize("&7Edit the lore of the item you\n&7have in your hand!\n\n&eClick to edit!")));
        inventory.setItem(14, makeItem(Material.GOLDEN_APPLE, "§aEdit Item Stats", 1, 0, IUtil.colorize("&7Edit the stats the item has!\n&7Including Defense, Health, and Intelligence!\n\n&eClick to edit!")));
        inventory.setItem(12, makeItem(Material.PAINTING, "§aSet item Rarity", 1, 0, IUtil.colorize("&7Set the rarity of your item\n&7you can choose anything\n&7between: &fCommon&7, &aUncommon\n&9Rare&7, &5Epic&7, &6Legendary&7,\n&dMythic&7, &cSpecial&7.\n\n§cNote: The last line of\n§clore in your item will\n§cturn into the rarity name.\n\n&eClick to set!")));
        inventory.setItem(15, makeItem(Material.GLOWSTONE_DUST, "§aSet item ability", 1, 0, "§c§lCOMING SOON"));
        inventory.setItem(22, makeItem(Material.ENCHANTED_BOOK, "§aEdit Item Tags", 1, 0, IUtil.colorize("&7Edit the tags the item has!\n&7Including Unbreakable, Enchant tag,\n&7Glowing tag, and the Damage Tag!\n\n&eClick to edit!")));
        inventory.setItem(11,makeItem(Material.INK_SACK,"§aSet item color",1,5,IUtil.colorize("&7Edit the color of the item!\n&7you can change any item\n&7from: &aWool &7, &aStained_Clay &7, \n&aStained_Glass_Panes &7, &aStained_Glass  \n&7or &aINK_SACK(DYES)")));
    }
}
