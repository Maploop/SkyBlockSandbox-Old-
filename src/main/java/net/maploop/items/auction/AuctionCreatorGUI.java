package net.maploop.items.auction;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUIRevamped;
import net.maploop.items.gui.AnvilGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuctionCreatorGUI extends GUI {
    public final static Map<UUID, ItemStack> SELECTED_ITEM = new HashMap<>();

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
                    new ConfirmGUI(playerMenuUtility, ConfirmGUI.ConfirmReason.CREATE_AUCTION, this.bin, this.price, System.currentTimeMillis() + 5000).open();
                    break;
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
                            AnvilGUI gui = new AnvilGUI(player, e -> {
                                if (ItemUtilities.isInteger(e.getName())) {
                                    Bukkit.getScheduler().runTaskLater(Items.getInstance(), () -> {
                                        new AuctionCreatorGUI(playerMenuUtility, bin, Integer.parseInt(e.getName())).open();
                                    }, 3);
                                } else {
                                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                                    player.sendMessage("§cThat's not a valid number!");
                                }
                            });
                            gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Item price", 1, 0, "§f^^^^^\n§fYour item's price"));
                            gui.open();
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
                            AnvilGUI gui = new AnvilGUI(player, e -> {
                                if (ItemUtilities.isInteger(e.getName())) {
                                    Bukkit.getScheduler().runTaskLater(Items.getInstance(), () -> {
                                        new AuctionCreatorGUI(playerMenuUtility, bin, Integer.parseInt(e.getName())).open();
                                    }, 3);
                                } else {
                                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                                    player.sendMessage("§cThat's not a valid number!");
                                }
                            });
                            gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Item price", 1, 0, "§f^^^^^\n§fYour item's price"));
                            gui.open();
                        }
                    }
                    return;
                }
                break;
        }

        if (event.getCurrentItem() == null) return;

        if (SELECTED_ITEM.containsKey(player.getUniqueId())) {
            if (event.getSlot() == 13) {
                event.getInventory().setItem(13, makeItem(Material.STONE_BUTTON, "§ePut Item", 1, 0));
                player.getInventory().addItem(SELECTED_ITEM.get(player.getUniqueId()));
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 0);
                SELECTED_ITEM.remove(player.getUniqueId());
                inventory.setItem(29, makeItem(Material.STAINED_CLAY, "§cCreate", 1, 14, "§cCan't create auction!"));
            }
            return;
        }

        if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
            inventory.setItem(13, event.getCurrentItem());
            SELECTED_ITEM.put(player.getUniqueId(), event.getCurrentItem());
            player.getInventory().setItem(event.getSlot(), null);
            player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 2);
            inventory.setItem(29, makeItem(Material.STAINED_CLAY, "§aCreate", 1, 5, "§eClick to create auction!"));
        } else {
            player.sendMessage("§cYour item must have a displayname or lore in order to be auctioned!");
        }
    }

    @Override
    public void setItems() {
        DecimalFormat df = new DecimalFormat("#,###");
        Player player = playerMenuUtility.getOwner();

        setFilter();
        if (SELECTED_ITEM.containsKey(player.getUniqueId()))
            inventory.setItem(29, makeItem(Material.STAINED_CLAY, "§aCreate", 1, 5, "§eClick to create auction!"));
        else
            inventory.setItem(29, makeItem(Material.STAINED_CLAY, "§cCreate", 1, 14, "§cCan't create auction!"));

        if (SELECTED_ITEM.containsKey(player.getUniqueId()))
            inventory.setItem(13, SELECTED_ITEM.get(player.getUniqueId()));
        else
            inventory.setItem(13, makeItem(Material.STONE_BUTTON, "§ePut Item", 1, 0));

        if (this.bin)
            inventory.setItem(48, makeItem(Material.POWERED_RAIL, "§aSwitch to Auction", 1, 0));
        else
            inventory.setItem(48, makeItem(Material.GOLD_INGOT, "§aSwitch to BIN", 1, 0));

        if (this.bin) {
            inventory.setItem(31, makeItem(Material.GOLD_INGOT, "§fItem price: §6" + df.format(this.price) + " coins", 1, 0, IUtil.colorize("&7The price at which you want to\n&7sell this item.\n\n&7Extra fee: &6" + (50 + (this.price * 0.01)) + " coins\n\n&eClick to edit!")));
        } else {
            inventory.setItem(31, makeItem(Material.POWERED_RAIL, "§fStarting bid: §6" + df.format(this.price) + " coins", 1, 0, "§eClick to edit!"));
        }
    }
}
