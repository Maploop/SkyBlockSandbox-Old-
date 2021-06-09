package net.maploop.items.auction;

import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.mongo.MongoConnect;
import net.maploop.items.util.BukkitSerialization;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class YourAuctionsGUI extends GUI {
    private final static MongoConnect DB = new MongoConnect();
    private final static List<AuctionItem> ITEMS = new ArrayList<>();

    public YourAuctionsGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Your Auctions";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        if (new ItemUtilities.AuctionData().isAuctionedItem(event.getCurrentItem())) {
            String id = new ItemUtilities.AuctionData().getString(event.getCurrentItem(), "id");

            AuctionItem i = new AuctionItemHandler(UUID.fromString(id),
                    Integer.parseInt(DB.getData("auctions", id, "price").toString()),
                    Bukkit.getOfflinePlayer(UUID.fromString(DB.getData("auctions", id, "owner").toString())),
                    BukkitSerialization.itemStackFromBase64(DB.getData("auctions", id, "item-stack").toString()),
                    Boolean.parseBoolean(DB.getData("auctions", id, "bin").toString()),
                    Long.parseLong(DB.getData("auctions", id, "end-time").toString())
            );

            new AuctionInspectGUI(playerMenuUtility, i).open();
            return;
        }

        switch (event.getSlot()) {
            case 31: {
                new AuctionHouseGUI(playerMenuUtility).open();
                break;
            }
            case 32: {
                new AuctionCreatorGUI(playerMenuUtility, false, 500).open();
                break;
            }
            default:
                if (event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
        }
    }

    @Override
    public void setItems() {
        fillBorder();

        ITEMS.clear();

        for (Document doc : DB.getAllDocuments("auctions")) {
            if (doc.get("owner").equals(playerMenuUtility.getOwner().getUniqueId().toString())) {
                this.put(new AuctionItemHandler(UUID.fromString(doc.get("id").toString()), Integer.parseInt(doc.get("price").toString()),
                        Bukkit.getOfflinePlayer(UUID.fromString(doc.get("owner").toString())),
                        BukkitSerialization.itemStackFromBase64(doc.get("item-stack").toString()),
                        Boolean.parseBoolean(doc.get("bin").toString()),
                        Long.parseLong(doc.get("end-time").toString()))
                );
            }
        }

        for (AuctionItem item : ITEMS) {
            ItemStack s = item.getStack();
            ItemStack s1 = new ItemUtilities.AuctionData().put(s, "id", item.getId().toString());

            ItemMeta meta = s1.getItemMeta();
            List<String> lore = new ArrayList<>(s1.getItemMeta().getLore());
            lore.add("§8§m---------------");
            lore.add("§7Seller: " + item.getOwner().getPlayer().getDisplayName());
            lore.add(item.isBIN() ? "§7Buy it now: §6" + item.getPrice() : "§7Starting bid: §6" + new DecimalFormat("#,###").format(item.getPrice()));
            lore.add("");
            lore.add(item.isBIN() ? "" : "§7Highest bid: §6" + new DecimalFormat("#,###").format(Integer.parseInt(DB.getData("auctions", item.getId().toString(), "top-bid-amount").toString())));
            lore.add("§7Bidder: " + (item.getHighestBidder() != null ? item.getHighestBidder().getPlayer().getDisplayName() : "§8None"));
            lore.add("");
            lore.add("§eClick to inspect!");
            meta.setLore(lore);
            s1.setItemMeta(meta);

            inventory.addItem(s1);
        }

        inventory.setItem(31, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Auction House"));
        inventory.setItem(32, makeItem(Material.IRON_BARDING, "§aCreate Auction", 1, 0, "§eClick to become rich!"));
    }

    public void put(AuctionItem item) {
        ITEMS.add(item);
    }
}
