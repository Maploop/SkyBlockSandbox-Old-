package net.maploop.items.gui.itemCreator;

import net.maploop.items.gui.MinecraftItemsGUI;
import net.maploop.items.gui.PaginatedGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.SBItems;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DyeGUI extends PaginatedGUI {

    public DyeGUI(PlayerMenuUtility playerMenuUtility, ItemStack itemStack) {
        super(playerMenuUtility,itemStack);

    }

    @Override
    public String getTitle() {
        return "Dye an item";
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
        if (event.getCurrentItem().getType() == Material.AIR) return;
        if (event.getCurrentItem() == null) return;

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
        if (itemStack.getType() == Material.INK_SACK) {
            switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§aBlack Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 0, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 0);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aRed Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 1, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 1);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aGreen Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 2, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 2);
                        player.getInventory().addItem(item);

                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aBrown Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 3, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 3);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aBlue Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 4, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 4);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aPurple Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 5, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 5);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aCyan Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 6, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 6);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aLight Gray Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 7, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 7);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aGray Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 8, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 8);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aPink Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 9, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 9);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aLime Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 10, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 10);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aYellow Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 11, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 11);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aLight Blue Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 12, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 12);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
                case "§aMagenta Color": {
                    try {
                        ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 13, itemStack.getItemMeta().getLore());
                        player.getInventory().remove(itemStack);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        ItemStack item = makeItem(itemStack.getType(), null, 1, 13);
                        player.getInventory().addItem(item);
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                    break;
                }
            }

        } else {
        switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§aBlack Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 15, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 15);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aRed Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 14, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 14);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aGreen Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 13, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 13);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aBrown Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 12, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 12);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aBlue Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 11, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 11);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aPurple Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 10, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 10);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aCyan Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 9, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 9);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aLight Gray Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 8, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 8);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aGray Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 7, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 8);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aPink Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 6, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 6);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aLime Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 5, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 5);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aYellow Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 4, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 4);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aLight Blue Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 3, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 3);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aMagenta Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 2, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 2);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aOrange Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 1, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 1);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
            case "§aWhite Color": {
                try {
                    ItemStack item = makeItem(itemStack.getType(), itemStack.getItemMeta().getDisplayName(), 1, 0, itemStack.getItemMeta().getLore());
                    player.getInventory().remove(itemStack);
                    player.getInventory().addItem(item);
                    player.closeInventory();
                } catch (NullPointerException e) {
                    ItemStack item = makeItem(itemStack.getType(), null, 1, 0);
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                }
                break;
            }
        }
        }
    }

    @Override
    public void setItems() {
        setFilter();

        List<ItemStack> items = new ArrayList<>(SBItems.getItems());

        if(itemStack.getType().equals(Material.INK_SACK)) {

            ItemStack inksack = makeItem(itemStack.getType(), "§aBlack Color", 1, 0,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(10, inksack);
            ItemStack reddye = makeItem(itemStack.getType(), "§aRed Color", 1, 1,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(11, reddye);
            ItemStack greendye = makeItem(itemStack.getType(), "§aGreen Color", 1, 2,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(12, greendye);
            ItemStack browndye = makeItem(itemStack.getType(), "§aBrown Color", 1, 3,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(13, browndye);
            ItemStack bluedye = makeItem(itemStack.getType(), "§aBlue Color", 1, 4,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(14, bluedye);
            ItemStack purpledye = makeItem(itemStack.getType(), "§aPurple Color", 1, 5,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(15, purpledye);
            ItemStack cyandye = makeItem(itemStack.getType(), "§aCyan Color", 1, 6,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(16, cyandye);
            ItemStack lightgraydye = makeItem(itemStack.getType(), "§aLight Gray Color", 1, 7,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(19, lightgraydye);
            ItemStack graydye = makeItem(itemStack.getType(), "§aGray Color", 1, 8,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(20, graydye);
            ItemStack pinkdye = makeItem(itemStack.getType(), "§aPink Color", 1, 9,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(21, pinkdye);
            ItemStack limedye = makeItem(itemStack.getType(), "§aLime Color", 1, 10,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(22, limedye);
            ItemStack yellowdye = makeItem(itemStack.getType(), "§aYellow Color", 1, 11,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(23, yellowdye);
            ItemStack lightbluedye = makeItem(itemStack.getType(), "§aLight Blue Color", 1, 12,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(24, lightbluedye);
            ItemStack magentadye = makeItem(itemStack.getType(), "§aMagenta Color", 1, 13,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(25, magentadye);
            ItemStack orangedye = makeItem(itemStack.getType(), "§aOrange Color", 1, 14,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(30, orangedye);
            ItemStack whitedye = makeItem(itemStack.getType(), "§aWhite Color", 1, 15,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(32, whitedye);
        } else {
            ItemStack inksack = makeItem(itemStack.getType(), "§aBlack Color", 1, 15,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(10, inksack);
            ItemStack reddye = makeItem(itemStack.getType(), "§aRed Color", 1, 14,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(11, reddye);
            ItemStack greendye = makeItem(itemStack.getType(), "§aGreen Color", 1, 13,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(12, greendye);
            ItemStack browndye = makeItem(itemStack.getType(), "§aBrown Color", 1, 12,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(13, browndye);
            ItemStack bluedye = makeItem(itemStack.getType(), "§aBlue Color", 1, 11,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(14, bluedye);
            ItemStack purpledye = makeItem(itemStack.getType(), "§aPurple Color", 1, 10,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(15, purpledye);
            ItemStack cyandye = makeItem(itemStack.getType(), "§aCyan Color", 1, 9,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(16, cyandye);
            ItemStack lightgraydye = makeItem(itemStack.getType(), "§aLight Gray Color", 1, 8,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(19, lightgraydye);
            ItemStack graydye = makeItem(itemStack.getType(), "§aGray Color", 1, 7,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(20, graydye);
            ItemStack pinkdye = makeItem(itemStack.getType(), "§aPink Color", 1, 6,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(21, pinkdye);
            ItemStack limedye = makeItem(itemStack.getType(), "§aLime Color", 1, 5,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(22, limedye);
            ItemStack yellowdye = makeItem(itemStack.getType(), "§aYellow Color", 1, 4,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(23, yellowdye);
            ItemStack lightbluedye = makeItem(itemStack.getType(), "§aLight Blue Color", 1, 3,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(24, lightbluedye);
            ItemStack magentadye = makeItem(itemStack.getType(), "§aMagenta Color", 1, 2,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(25, magentadye);
            ItemStack orangedye = makeItem(itemStack.getType(), "§aOrange Color", 1, 1,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(30, orangedye);
            ItemStack whitedye = makeItem(itemStack.getType(), "§aWhite Color", 1, 0,
                    "§7Change the item color!\n\n§eClick to Change!");
            inventory.setItem(32, whitedye);
        }

        ItemStack close = makeItem(Material.BARRIER, "§cClose", 1, 0);
        inventory.setItem(49, close);
    }
}
