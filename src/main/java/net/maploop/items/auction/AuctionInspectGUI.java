package net.maploop.items.auction;

import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.mongo.MongoConnect;
import net.maploop.items.util.Log;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionInspectGUI extends GUI {
    private final static MongoConnect DB = new MongoConnect();
    private final static DecimalFormat DF = new DecimalFormat("#,###");

    private final AuctionItem item;

    public AuctionInspectGUI(PlayerMenuUtility playerMenuUtility, AuctionItem item) {
        super(playerMenuUtility);
        this.item = item;
    }

    @Override
    public String getTitle() {
        return item.isBIN() ? "BIN Auction View" : "Auction View";
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
            case ARROW:
                new AuctionBrowserGUI(playerMenuUtility, AuctionBrowserGUI.AuctionCategory.WEAPONS).open();
                break;
            case GOLD_NUGGET: {
                if (!item.isBIN()) {
                    player.closeInventory();
                    try {
                        AuctionItem currItem = item;
                        currItem.setHighestBid((int) (currItem.getHighestBid() + (currItem.getHighestBid() * 0.2)));
                        currItem.setHighestBidder(player);
                    } catch (Exception ex) {
                        player.sendMessage("§cFailed to place your bid! §7(" + ex.getMessage().toUpperCase() + ")");
                        Log.severe("Failed to place bid for " + item.getId().toString() + "!");
                        Log.severe("Exception message: " + ex.getMessage().toUpperCase());
                        return;
                    }
                    player.sendMessage("§aBid of §6" + DF.format(item.getHighestBid()) + " coins §aplaced for " + item.getStack().getItemMeta().getDisplayName() + "§a!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                } else {
                    try {
                        player.getInventory().addItem(new ItemUtilities.AuctionData().remove(item.getStack()));
                        item.setEnded(true);
                        item.setBought(true);
                        item.setBuyer(player.getUniqueId().toString());
                        player.closeInventory();
                    } catch (Exception ex) {
                        Log.severe("Failed to save purchase item " + item.getId().toString());
                        player.sendMessage("§cFailed to purchase item! Please report this!");
                        return;
                    }
                    player.sendMessage("§aSuccessfully bought " + item.getStack().getItemMeta().getDisplayName() + " §a for §6" + DF.format(item.getPrice()) + " coins§a!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                }
                break;
            }

            case GOLD_BLOCK: {
                if (item.isBought()) {
                    if (item.getOwner().getUniqueId().equals(player.getUniqueId())) {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                        player.sendMessage("§aClaimed §6" + new DecimalFormat("#,###").format(item.getHighestBid()) + " coins§a!");
                        player.closeInventory();
                        item.remove();
                    }
                } else {
                    if (item.getOwner().getUniqueId().equals(player.getUniqueId())) {
                        player.getInventory().addItem(item.getStack());
                        item.remove();
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                        break;
                    }
                }

                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        Player player = playerMenuUtility.getOwner();

        ItemStack s = item.getStack();
        ItemStack s1 = new ItemUtilities.AuctionData().put(s, "id", item.getId().toString());

        ItemMeta meta = s1.getItemMeta();
        List<String> lore = new ArrayList<>(s1.getItemMeta().getLore());
        lore.add("§8§m---------------");
        lore.add("§7Seller: " + item.getOwner().getPlayer().getDisplayName());
        lore.add(item.isBIN() ? "§7Buy it now: §6" + item.getPrice() : "§7Starting bid: §6" + DF.format(item.getPrice()));
        lore.add("");
        lore.add(item.isBIN() ? "" : "§7Highest bid: §6" + new DecimalFormat("#,###").format(Integer.parseInt(DB.getData("auctions", item.getId().toString(), "top-bid-amount").toString())));
        lore.add("§7Bidder: " + (item.getHighestBidder() != null ? item.getHighestBidder().getPlayer().getDisplayName() : "§8None"));
        lore.add("");
        lore.add(item.isEnded() ? "§7Status: §cEnded!" : "§7Status: §8Pending...");
        lore.add("");
        lore.add(item.getOwner().getUniqueId().equals(player.getUniqueId()) ? "§aYou own this auction!" : "§aCurrently inspecting!");
        meta.setLore(lore);
        s1.setItemMeta(meta);

        inventory.setItem(13, s1);

        if (item.isEnded()) {
            if (item.isBought()) {
                if (item.getOwner().getUniqueId().equals(player.getUniqueId())) {
                    inventory.setItem(31, makeItem(Material.GOLD_BLOCK, "§6Claim Coins", 1, 0, "§7Amount: §6" + item.getHighestBid() + " coins\n\n§eClick to claim!"));
                } else if (Bukkit.getOfflinePlayer(UUID.fromString(item.getBuyer())) != null && Bukkit.getOfflinePlayer(UUID.fromString(item.getBuyer())).getUniqueId().equals(player.getUniqueId())) {
                    inventory.setItem(31, makeItem(Material.GOLD_BLOCK, "§6Claim Items", 1, 0, "§eClick to claim!"));
                }
            } else {
                if (item.getOwner().getUniqueId().equals(player.getUniqueId())) {
                    inventory.setItem(31, makeItem(Material.GOLD_BLOCK, "§6Claim Items", 1, 0, "§eClick to claim!"));
                }
            }
        } else {
            if (Boolean.parseBoolean(DB.getData("auctions", item.getId().toString(), "bin").toString())) {
                if (item.getOwner().getUniqueId().equals(player.getUniqueId())) {
                    inventory.setItem(31, makeItem(Material.POISONOUS_POTATO, "§6Buy item", 1, 0, "§7item price: §6" + DF.format(item.getPrice()) + " coins\n\n§cYou cannot buy your own auction!"));
                    return;
                }
                inventory.setItem(31, makeItem(Material.GOLD_NUGGET, "§6Buy item", 1, 0, "§7item price: §6" + DF.format(item.getPrice()) + " coins\n\n§eClick to buy it now!"));
            } else {
                if (item.getOwner().getUniqueId().equals(player.getUniqueId())) {
                    inventory.setItem(29, makeItem(Material.POISONOUS_POTATO, "§6Submit bid", 1, 0, "§7New bid: §6" + (item.getHighestBid() + (item.getPrice() * 0.2)) + " coins\n\n§cYou cannot bid on your own auction!"));
                    return;
                }
                inventory.setItem(29, makeItem(Material.GOLD_NUGGET, "§6Submit bid", 1, 0, "§7New bid: §6" + (item.getHighestBid() + (item.getPrice() * 0.2)) + " coins\n\n§eClick to submit bid!"));
            }
        }

        if (item.getOwner().getUniqueId().equals(player.getUniqueId())) {
            inventory.setItem(33, makeItem(Material.STAINED_CLAY, "§cCancel Auction", 1, 14, "§eClick to cancel!"));
        }

        inventory.setItem(49, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Auction Browser"));
    }
}
