package net.maploop.items.auction;

import net.maploop.items.item.ItemUtilities;
import net.maploop.items.mongo.MongoConnect;
import net.maploop.items.util.BukkitSerialization;
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

    boolean isEnded();

    boolean isBought();

    void setBought(boolean b);

    void setEnded(boolean b);

    void setBuyer(String uuid);

    String getBuyer();

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
                DB.setData("auctions", this.getId().toString(), "owner", this.getOwner().getUniqueId().toString());
                DB.setData("auctions", this.getId().toString(), "bin", this.isBIN());
                DB.setData("auctions", this.getId().toString(), "price", this.getPrice());
                DB.setData("auctions", this.getId().toString(), "top-bid-amount", this.getHighestBid());
                DB.setData("auctions", this.getId().toString(), "end-time", this.getTimeEnding());
                DB.setData("auctions", this.getId().toString(), "top-bidder", (this.getHighestBidder() == null ? "none" : this.getHighestBidder().getUniqueId().toString()));
                DB.setData("auctions", this.getId().toString(), "item-stack", BukkitSerialization.itemStackToBase64(this.getStack()));

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
            public boolean isEnded() {
                return false;
            }

            @Override
            public boolean isBought() {
                return false;
            }

            @Override
            public void setBought(boolean b) {

            }

            @Override
            public void setEnded(boolean b) {

            }

            @Override
            public void setBuyer(String uuid) {

            }

            @Override
            public String getBuyer() {
                return null;
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
                DB.setData("auctions", this.id, "top-bid-amount", i);
            }

            @Override
            public void setHighestBidder(Player player) {
                DB.setData("auctions", this.id, "top-bidder", player.getUniqueId().toString());
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
