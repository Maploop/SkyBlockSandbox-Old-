package net.maploop.items.auction;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUIRevamped;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuctionCreatorGUI extends GUI {

    private final boolean bin;
    private final int price;

    public AuctionCreatorGUI(PlayerMenuUtility playerMenuUtility, boolean bin, int price) {
        super(playerMenuUtility);
        this.bin = bin;
        this.price = price;
    }

    @Override
    public String getTitle() {
        return bin ? "Create BIN Auction" : "Create Auction";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case STAINED_CLAY: {
                if (event.getCurrentItem().getDurability() == (byte) 14)
                    return;
                if (event.getCurrentItem().getDurability() == 5) {
                    AuctionBrowserGUI.put(new AuctionItemHandler(UUID.randomUUID(), this.price, player, inventory.getItem(13), this.bin, System.currentTimeMillis() + 500).save());
                    player.closeInventory();
                    player.sendMessage("§aCreated!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                }
                return;
            }
            case STONE_BUTTON:
            case STAINED_GLASS_PANE: return;

            case GOLD_INGOT:
                if (event.getSlot() == 48 || event.getSlot() == 31) {
                    if (event.getCurrentItem().hasItemMeta()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("§aSwitch to BIN")) {
                            new AuctionCreatorGUI(playerMenuUtility, true, (this.price)).open();
                        }
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("price")) {
                            SignGUIRevamped.openSignEditor(player, new String[] {"", "^^^^^", "Your auction", "item price"}, e -> {
                                if (ItemUtilities.isInteger(e.getSignText()[0])) {
                                    Bukkit.getScheduler().runTaskLater(Items.getInstance(), () -> {
                                        new AuctionCreatorGUI(playerMenuUtility, bin, Integer.parseInt(e.getSignText()[0])).open();
                                    }, 3);
                                } else {
                                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                                    player.sendMessage("§cThat's not a valid number!");
                                }
                            });
                        }
                    }
                    return;
                }
                break;
            case POWERED_RAIL:
                if (event.getSlot() == 48 || event.getSlot() == 31) {
                    if (event.getCurrentItem().hasItemMeta()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("§aSwitch to Auction")) {
                            new AuctionCreatorGUI(playerMenuUtility, false, this.price).open();
                        }
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("bid")) {
                            SignGUIRevamped.openSignEditor(player, new String[] {"", "^^^^^", "Your auction", "starting bid"}, e -> {
                                if (ItemUtilities.isInteger(e.getSignText()[0])) {
                                    Bukkit.getScheduler().runTaskLater(Items.getInstance(), () -> {
                                        new AuctionCreatorGUI(playerMenuUtility, bin, Integer.parseInt(e.getSignText()[0])).open();
                                    }, 3);
                                } else {
                                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                                    player.sendMessage("§cThat's not a valid number!");
                                }
                            });
                        }
                    }
                    return;
                }
                break;
        }

        if (event.getCurrentItem() == null) return;

        inventory.setItem(13, event.getCurrentItem());
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 2);
        inventory.setItem(29, makeItem(Material.STAINED_CLAY, "§aCreate", 1, 5, "§eClick to create auction!"));
    }

    @Override
    public void setItems() {
        DecimalFormat df = new DecimalFormat("#,###");

        setFilter();
        inventory.setItem(29, makeItem(Material.STAINED_CLAY, "§cCreate", 1, 14, "§cCan't create auction!"));

        inventory.setItem(13, makeItem(Material.STONE_BUTTON, "§ePut Item", 1, 0));

        if (this.bin)
            inventory.setItem(48, makeItem(Material.POWERED_RAIL, "§aSwitch to Auction", 1, 0));
        else
            inventory.setItem(48, makeItem(Material.GOLD_INGOT, "§aSwitch to BIN", 1, 0));

        if (this.bin) {
            inventory.setItem(31, makeItem(Material.GOLD_INGOT, "§fItem price: §6" + df.format(this.price) + " coins", 1, 0, IUtil.colorize("&7The price at which you want to\n&7sell this item.\n\nExtra fee: &650 coins\n\n&eClick to edit!")));
        } else {
            inventory.setItem(31, makeItem(Material.POWERED_RAIL, "§fStarting bid: §6" + df.format(this.price) + " coins", 1, 0, "§eClick to edit!"));
        }
    }
}
