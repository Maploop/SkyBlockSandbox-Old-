package net.maploop.items.auction;

import net.maploop.items.mongo.MongoConnect;
import org.bukkit.entity.Player;

public interface AuctionBidder {
    Player toBukkitPlayer();

    int getBid();

    void setBid(int i);

    class AuctionBidderHandler implements AuctionBidder {
        private final static MongoConnect DB = new MongoConnect();

        private final AuctionItem item;
        private final int bid;
        private final Player player;

        public AuctionBidderHandler(AuctionItem item, Player player, int bid) {
            this.bid = bid;
            this.player = player;
            this.item = item;
        }


        @Override
        public Player toBukkitPlayer() {
            return this.player;
        }

        @Override
        public int getBid() {
            return bid;
        }


        // This isn't working right now
        // Haven't implemented an actual way.
        @Override
        public void setBid(int i) {
            DB.setData("items", item.getId().toString(), "current-bidder", this.player.getUniqueId());
            DB.setData("items", item.getId().toString(), "top-bid-amount", i);
        }
    }
}
