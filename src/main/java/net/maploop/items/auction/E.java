package net.maploop.items.auction;

import net.maploop.items.mongo.MongoConnect;
import java.util.UUID;

public class E {
    public static void main(String[] args) {
        final MongoConnect DB = new MongoConnect();
        DB.connect();

        DB.insertAuctionItem(UUID.randomUUID().toString(), "none", null, 50, 50, "2cb690ce-b5ef-480c-9907-5ec6ad21398b", false, (System.currentTimeMillis() + (500 * 1000)));

        new Thread(() -> {
            try { Thread.sleep(300); } catch (Exception ex) { ex.printStackTrace(); }

            DB.insertAuctionItem(UUID.randomUUID().toString(), "2227d38f-dda0-48bf-8051-1e79f50e9eaa", null, 50, 50, "2cb690ce-b5ef-480c-9907-5ec6ad21398b", false, (System.currentTimeMillis() + (500 * 1000)));
        }).start();
    }
}
