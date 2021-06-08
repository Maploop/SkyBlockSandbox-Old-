package net.maploop.items.mongo;

import com.mongodb.DBCursor;
import com.mongodb.client.*;
import net.maploop.items.Items;
import net.maploop.items.util.BukkitSerialization;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MongoConnect {
    private static MongoClient client;
    private static MongoDatabase database;

    public MongoConnect() {

    }

    public void connect() {
        client = MongoClients.create(Items.getInstance().getConfig().getString("mongo-uri"));
        database = client.getDatabase(Items.getInstance().getConfig().getString("mongo-database"));

        System.out.println("MongoDB Successfully connected");
    }

    public void setData(String collection, String id, String key, Object value) {
        MongoCollection<Document> col = database.getCollection(collection);

        Document query = new Document("id", id);
        Document found = col.find(query).first();

        if (found == null) {
            Document update = new Document("id", id).append(key, value);
            col.insertOne(update);
            return;
        }

        col.updateOne(found, found.append(key, value));
    }

    public void insertAuctionItem(String id, String topBidder, ItemStack stack, int topBid, int price, String owner, boolean bin, long endTime) {
        MongoCollection<Document> col = database.getCollection("auctions");

        Document query = new Document("id", id);
        Document found = col.find(query).first();

        if (found == null) {
            Document doc = new Document("id", id);
            doc.put("owner", owner);
            doc.put("bin", bin);
            doc.put("price", price);
            doc.put("top-bid-amount", topBid);
            doc.put("end-time", endTime);
            doc.put("top-bidder", topBidder);
            doc.put("item-stack", BukkitSerialization.itemStackToBase64(stack));

            col.insertOne(doc);
            return;
        }
        Document doc = new Document("id", id);
        doc.put("owner", owner);
        doc.put("bin", bin);
        doc.put("price", price);
        doc.put("top-bid-amount", topBid);
        doc.put("end-time", endTime);
        doc.put("top-bidder", topBidder);
        doc.put("item-stack", BukkitSerialization.itemStackToBase64(stack));

        col.updateOne(found, doc);
    }

    public Object getData(String collection, String id, String key) {
        MongoCollection<Document> col = database.getCollection(collection);

        Document query = new Document("id", id);
        Document found = col.find(query).first();

        if (found != null)
            return found.get(key);
        return null;
    }

    public boolean remove(String collection, String id) {
        MongoCollection<Document> col = database.getCollection(collection);

        Document query = new Document("id", id);
        Document found = col.find(query).first();

        if (found == null) return false;

        col.deleteOne(found);
        return true;
    }

    public List<Document> getAllDocuments(String collection) {
        MongoCollection<Document> col = database.getCollection(collection);
        FindIterable<Document> docs = col.find();
        MongoCursor<Document> cursor = docs.iterator();

        List<Document> found = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                found.add(cursor.next());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return found;
    }
}
