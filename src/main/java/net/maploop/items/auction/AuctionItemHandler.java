package net.maploop.items.auction;

import net.maploop.items.Items;
import net.maploop.items.mongo.MongoConnect;
import net.maploop.items.util.BukkitSerialization;
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
        DB.setData("auctions", this.getId().toString(), "owner", this.getOwner().getUniqueId().toString());
        DB.setData("auctions", this.getId().toString(), "bin", this.isBIN());
        DB.setData("auctions", this.getId().toString(), "price", this.getPrice());
        DB.setData("auctions", this.getId().toString(), "top-bid-amount", this.getHighestBid());
        DB.setData("auctions", this.getId().toString(), "end-time", this.getTimeEnding());
        DB.setData("auctions", this.getId().toString(), "top-bidder", (this.getHighestBidder() == null ? "none" : this.getHighestBidder().getUniqueId().toString()));
        DB.setData("auctions", this.getId().toString(), "item-stack", BukkitSerialization.itemStackToBase64(this.getStack()));

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
        DB.setData("auctions", this.getId().toString(), "top-bid-amount", i);
    }

    @Override
    public void setHighestBidder(Player player) {
        DB.setData("auctions", this.getId().toString(), "top-bidder", player.getUniqueId().toString());
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
        for (AuctionItem item : AuctionBrowserGUI.ITEMS) {
            if (item.getId() == this.getId()) {
                AuctionBrowserGUI.ITEMS.remove(item);
                break;
            }
        }
        DB.remove("auctions", this.getId().toString());
    }

    @Override
    public boolean isEnded() {
        if (DB.getData("auctions", this.getId().toString(), "ended") == null) return false;

        return Boolean.parseBoolean(DB.getData("auctions", this.getId().toString(), "ended").toString());
    }

    @Override
    public boolean isBought() {
        if (DB.getData("auctions", this.getId().toString(), "bought") == null) return false;

        return Boolean.parseBoolean(DB.getData("auctions", this.getId().toString(), "bought").toString());
    }

    @Override
    public void setBought(boolean b) {
        DB.setData("auctions", this.getId().toString(), "bought", String.valueOf(b));
    }

    @Override
    public void setEnded(boolean b) {
        DB.setData("auctions", this.getId().toString(), "ended", String.valueOf(b));
    }

    @Override
    public void setBuyer(String uuid) {
        DB.setData("auctions", this.getId().toString(), "buyer", uuid);
    }

    @Override
    public String getBuyer() {
        if (DB.getData("auctions", this.getId().toString(), "buyer") == null) return null;

        return DB.getData("auctions", this.getId().toString(), "buyer").toString();
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
