package net.maploop.items.auction;

import net.maploop.items.item.ItemUtilities;
import net.maploop.items.mongo.MongoConnect;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface AuctionItem {
    UUID getId();

    OfflinePlayer getOwner();

    AuctionItem save();

    ItemStack getStack();

    int getPrice();

    boolean isBIN();

    void remove();

    int getHighestBid();

    OfflinePlayer getHighestBidder();

    void setHighestBid(int i);

    void setHighestBidder(Player player);

    long getTimeEnding();

    AuctionBrowserGUI.AuctionCategory getCategory();

    static AuctionItem fromItemStack(ItemStack stack) {
        return new AuctionItem() {
            private String id = null;
            private final MongoConnect DB = new MongoConnect();

            @Override
            public UUID getId() {
                this.id = new ItemUtilities.AuctionData().getString(stack, "id");
                return UUID.fromString(new ItemUtilities.AuctionData().getString(stack, "id"));
            }

            @Override
            public Player getOwner() {
                return Bukkit.getPlayer(UUID.fromString(this.id));
            }

            @Override
            public AuctionItem save() {
                DB.insertAuctionItem(this.getId().toString(), (this.getHighestBidder() == null ? "none" : this.getHighestBidder().getUniqueId().toString()), this.getStack(), this.getPrice(), this.getPrice(), this.getOwner().getUniqueId().toString(), this.isBIN(), this.getTimeEnding());

                return this;
            }

            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public int getPrice() {
                return Integer.parseInt(DB.getData("auctions", this.id, "item-price").toString());
            }

            @Override
            public boolean isBIN() {
                return Boolean.parseBoolean(DB.getData("auctions", this.id, "bin").toString());
            }

            @Override
            public void remove() {
                AuctionBrowserGUI.ITEMS.remove(this);
                DB.remove("auctions", this.id);
            }

            @Override
            public int getHighestBid() {
                return Integer.parseInt(DB.getData("auctions", this.id, "top-bid-amount").toString());
            }

            @Override
            public OfflinePlayer getHighestBidder() {
                try {
                    return Bukkit.getPlayer(UUID.fromString(DB.getData("auctions", this.id.toString(), "top-bidder").toString()));
                } catch (Exception ex) {
                    return null;
                }
            }

            @Override
            public void setHighestBid(int i) {
                DB.insertAuctionItem(this.id, (this.getHighestBidder() == null ? "none" : this.getHighestBidder().getUniqueId().toString()), this.getStack(), i, this.getPrice(), this.getOwner().getUniqueId().toString(), this.isBIN(), this.getTimeEnding());
            }

            @Override
            public void setHighestBidder(Player player) {
                DB.insertAuctionItem(this.id, player.getUniqueId().toString(), this.getStack(), this.getHighestBid(), this.getPrice(), this.getOwner().getUniqueId().toString(), this.isBIN(), this.getTimeEnding());
            }

            @Override
            public long getTimeEnding() {
                return Long.parseLong(DB.getData("auctions", this.id, "end-time").toString());
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
            };
        };
    }
}
