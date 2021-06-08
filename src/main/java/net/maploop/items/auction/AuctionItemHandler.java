package net.maploop.items.auction;

import net.maploop.items.Items;
import net.maploop.items.mongo.MongoConnect;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class AuctionItemHandler implements AuctionItem {
    private final static MongoConnect DB = new MongoConnect();

    private final int price;
    private final OfflinePlayer owner;
    private final ItemStack stack;
    private final boolean isBIN;
    private final long timeEnding;
    private final UUID id;

    public AuctionItemHandler(UUID id, int price, OfflinePlayer owner, ItemStack stack, boolean isBIN, long timeEnding) {
        this.price = price;
        this.owner = owner;
        this.stack = stack;
        this.isBIN = isBIN;
        this.timeEnding = timeEnding;
        this.id = id;
    }

    @Override
    public UUID getId() {
        return this.id != null ? id : UUID.randomUUID();
    }

    @Override
    public OfflinePlayer getOwner() {
        return this.owner;
    }

    @Override
    public AuctionItem save() {
        DB.insertAuctionItem(this.getId().toString(), (this.getHighestBidder() == null ? "none" : this.getHighestBidder().getUniqueId().toString()), this.getStack(), this.getPrice(), this.getPrice(), this.getOwner().getUniqueId().toString(), this.isBIN(), this.getTimeEnding());

        Items.getInstance().getLogger().info("Saved auction with id " + this.getId() + "!");

        return this;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public OfflinePlayer getHighestBidder() {
        try {
            return Bukkit.getOfflinePlayer(UUID.fromString(DB.getData("auctions", this.id.toString(), "top-bidder").toString()));
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void setHighestBid(int i) {
        DB.insertAuctionItem(this.id.toString(), (this.getHighestBidder() == null ? "none" : this.getHighestBidder().getUniqueId().toString()), this.getStack(), i, this.getPrice(), this.getOwner().getUniqueId().toString(), this.isBIN(), this.getTimeEnding());
    }

    @Override
    public void setHighestBidder(Player player) {
        DB.insertAuctionItem(this.id.toString(), player.getUniqueId().toString(), this.getStack(), this.getHighestBid(), this.getPrice(), this.getOwner().getUniqueId().toString(), this.isBIN(), this.getTimeEnding());
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public boolean isBIN() {
        return this.isBIN;
    }

    @Override
    public void remove() {
        AuctionBrowserGUI.ITEMS.remove(this);
        DB.remove("auctions", this.getId().toString());
    }

    @Override
    public int getHighestBid() {
        return this.price;
    }

    @Override
    public long getTimeEnding() {
        return this.timeEnding;
    }

    @Override
    public AuctionBrowserGUI.AuctionCategory getCategory() {
        String e = stack.getType().toString().toLowerCase();

        if (e.contains("helmet")) {
            return AuctionBrowserGUI.AuctionCategory.ARMOR;
        } else if (e.contains("chestplate")) {
            return AuctionBrowserGUI.AuctionCategory.ARMOR;
        } else if (e.contains("leggings")) {
            return AuctionBrowserGUI.AuctionCategory.ARMOR;
        } else if (e.contains("boots")) {
            return AuctionBrowserGUI.AuctionCategory.ARMOR;
        } else if (e.contains("skull")) {
            return AuctionBrowserGUI.AuctionCategory.ACCESSORIES;
        } else if (stack.getType().isEdible()) {
            return AuctionBrowserGUI.AuctionCategory.CONSUMABLES;
        } else if (e.contains("sword") || e.contains("bow")) {
            return AuctionBrowserGUI.AuctionCategory.WEAPONS;
        } else if (stack.getType().isBlock()) {
            return AuctionBrowserGUI.AuctionCategory.BLOCKS;
        } else {
            return AuctionBrowserGUI.AuctionCategory.TOOLS_MISC;
        }
    }
}
