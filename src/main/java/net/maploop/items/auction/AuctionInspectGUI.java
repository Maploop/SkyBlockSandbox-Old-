package net.maploop.items.auction;

import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.mongo.MongoConnect;
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
                        player.sendMessage("§cFailed to place your bid! Please report this!");
                        Bukkit.getConsoleSender().sendMessage("§cFailed to place bid for " + item.getId().toString() + "!");
                        ex.printStackTrace();
                        return;
                    }
                    player.sendMessage("§aBid of §6" + DF.format(item.getHighestBid()) + " coins §aplaced for " + item.getStack().getItemMeta().getDisplayName() + "§a!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
                } else {
                    try {
                        player.getInventory().addItem(new ItemUtilities.AuctionData().remove(item.getStack()));
                        item.remove();
                    } catch (Exception ex) {
                        player.sendMessage("§cFailed to purchase item! Please report this!");
                        return;
                    }
                    player.sendMessage("§aSuccessfully bought " + item.getStack().getItemMeta().getDisplayName() + " §a for §6" + DF.format(item.getPrice()) + " coins§a!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
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
        lore.add("§aCurrently inspecting!");
        meta.setLore(lore);
        s1.setItemMeta(meta);

        inventory.setItem(13, s1);

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

        inventory.setItem(49, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Auction Browser"));
    }
}
