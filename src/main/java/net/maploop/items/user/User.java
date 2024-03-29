package net.maploop.items.user;

import jdk.nashorn.internal.ir.Block;
import net.maploop.items.auction.AuctionItemHandler;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.mongo.MongoConnect;
import net.maploop.items.util.Attribute;
import net.maploop.items.util.BukkitSerialization;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private final Player user;

    public User(Player player) {
        this.user = player;
    }

    private final static MongoConnect DB = new MongoConnect();

    private Map<Enchantment, Integer> ench_opt_1;
    private Map<Enchantment, Integer> ench_opt_2;
    private Map<Enchantment, Integer> ench_opt_3;
    public static final Map<Player, Double> currHealth = new HashMap<>();
    public static final Map<Player, Double> currIntelligence = new HashMap<>();
    public static final Map<Player, Double> currDefense = new HashMap<>();

    private static double strength;
    private static double crit_damage;
    private static double crit_chance;
    private static double defense;
    private static int speed;

    public Player getBukkitPlayer() {
        return user;
    }

    private double max_intelligence;
    private double max_health;

    private static String lastSkill;
    private static double gainedXP;
    private static Block block;

    private static double intelligence;
    private static double health;

    public Map<Enchantment, Integer> getEnch_opt_1() {
        return ench_opt_1;
    }

    public void setEnch_opt_1(Map<Enchantment, Integer> ench_opt_1) {
        this.ench_opt_1 = ench_opt_1;
    }

    public Map<Enchantment, Integer> getEnch_opt_2() {
        return ench_opt_2;
    }

    public void setEnch_opt_2(Map<Enchantment, Integer> ench_opt_2) {
        this.ench_opt_2 = ench_opt_2;
    }

    public Map<Enchantment, Integer> getEnch_opt_3() {
        return ench_opt_3;
    }

    public void setEnch_opt_3(Map<Enchantment, Integer> ench_opt_3) {
        this.ench_opt_3 = ench_opt_3;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double d) {
        strength = d;
    }

    public double getCrit_damage() {
        return crit_damage;
    }

    public void setCrit_damage(double d) {
        crit_damage = d;
    }

    public double getCrit_chance() {
        return crit_chance;
    }

    public void setCrit_chance(double d) {
        crit_chance = d;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double d) {
        defense = d;
    }

    public double getMax_intelligence() {
        return max_intelligence;
    }

    public void setMax_intelligence(double d) {
        max_intelligence = d;
    }

    public double getMax_health() {
        return max_health;
    }

    public void setMax_health(double d) {
        max_health = d;
    }

    public String getLastSkill() {
        return lastSkill;
    }

    public void setLastSkill(String d) {
        lastSkill = d;
    }

    public double getGainedXP() {
        return gainedXP;
    }

    public void setGainedXP(double d) {
        gainedXP = d;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block b) {
        block = b;
    }

    public double getIntelligence() {
        return currIntelligence.get(user);
    }

    public void setIntelligence(double d) {
        intelligence = d;
        currIntelligence.put(user, intelligence);
    }

    public double getHealth() {
        return currHealth.get(user);
    }

    public void setHealth(double d) {
        health = d;
        currHealth.put(user, health);
    }

    public FileConfiguration getPlayerData() {
        File f = new File("plugins/Items/PlayerData/" + user.getUniqueId().toString() + "/data.yml");
        FileConfiguration fc = new YamlConfiguration();
        try {
            fc.load(f);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return fc;
    }

    public double getTotalHealth() {
        File playerData = new File("plugins/Items/playerData/" + user.getUniqueId().toString() + "/data.yml");
        FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

        try {
            pD.load(playerData);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        double health = pD.getDouble("stats.extra_health");
        ItemStack[] armor = user.getEquipment().getArmorContents();
        for(ItemStack a : armor) {
            if(a != null && a.hasItemMeta() && a.getItemMeta().hasLore()) {
                health = health + ItemUtilities.getIntFromItem(a, Attribute.HEALTH.toString());
            }
        }
        return health + 100;
    }

    public double getTotalIntelligence() {
        File playerData = new File("plugins/Items/playerData/" + user.getUniqueId().toString() + "/data.yml");
        FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

        try {
            pD.load(playerData);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        double intelligence = pD.getDouble("stats.extra_mana");
        ItemStack[] armor = user.getEquipment().getArmorContents();
        for(ItemStack a : armor) {
            if(a != null && a.hasItemMeta() && a.getItemMeta().hasLore()) {
                intelligence = intelligence + ItemUtilities.getIntFromItem(a, "INTELLIGENCE");
            }
        }
        ItemStack iih = user.getItemInHand();
        if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
            intelligence = intelligence + ItemUtilities.getIntFromItem(iih, "INTELLIGENCE");
        }
        return intelligence + 100;
    }

    public double getTotalDefense() {
        File playerData = new File("plugins/Items/playerData/" + user.getUniqueId().toString() + "/data.yml");
        FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

        try {
            pD.load(playerData);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        double def = pD.getDouble("stats.extra_defense");
        ItemStack[] armor = user.getEquipment().getArmorContents();
        for(ItemStack a : armor) {
            if(a != null && a.hasItemMeta() && a.getItemMeta().hasLore()) {
                def = def + ItemUtilities.getIntFromItem(a, "DEFENSE");
            }
        }
        return def;
    }

    public double getTotalStrength() {
        File playerData = new File("plugins/Items/playerData/" + user.getUniqueId().toString() + "/data.yml");
        FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

        try {
            pD.load(playerData);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        double x = pD.getDouble("stats.extra_strength");
        ItemStack[] armor = user.getEquipment().getArmorContents();
        for(ItemStack a : armor) {
            if(a != null && a.hasItemMeta() && a.getItemMeta().hasLore()) {
                x = x + ItemUtilities.getIntFromItem(a, Attribute.STRENGTH.toString());
            }
        }
        ItemStack iih = user.getItemInHand();
        if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
            x = x + ItemUtilities.getIntFromItem(iih, Attribute.STRENGTH.toString());
        }
        return x;
    }

    public double getTotalCritDamage() {
        File playerData = new File("plugins/Items/playerData/" + user.getUniqueId().toString() + "/data.yml");
        FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

        try {
            pD.load(playerData);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        double x = pD.getDouble("stats.extra_crit_damage");
        ItemStack[] armor = user.getEquipment().getArmorContents();
        for(ItemStack a : armor) {
            if(a != null && a.hasItemMeta() && a.getItemMeta().hasLore()) {
                x = x + ItemUtilities.getIntFromItem(a, Attribute.CRIT_DAMAGE.toString());
            }
        }
        ItemStack iih = user.getItemInHand();
        if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
            x = x + ItemUtilities.getIntFromItem(iih, Attribute.CRIT_DAMAGE.toString());
        }
        return x + 50;
    }

    public double getTotalCritChance() {
        File playerData = new File("plugins/Items/playerData/" + user.getUniqueId().toString() + "/data.yml");
        FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);

        try {
            pD.load(playerData);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        double x = pD.getDouble("stats.extra_crit_chance");
        ItemStack[] armor = user.getEquipment().getArmorContents();
        for(ItemStack a : armor) {
            if(a != null && a.hasItemMeta() && a.getItemMeta().hasLore()) {
                x = x + ItemUtilities.getIntFromItem(a, Attribute.CRIT_CHANCE.toString());
            }
        }
        ItemStack iih = user.getItemInHand();
        if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
            x = x + ItemUtilities.getIntFromItem(iih, Attribute.CRIT_CHANCE.toString());
        }
        return x + 30;
    }

    public void playSound(String s, int vol, int pitch) {
        CraftPlayer player = (CraftPlayer) user;
        Location l = user.getLocation();
        player.getHandle().playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(s, l.getX(), l.getY(), l.getZ(), vol, pitch));
    }

    public boolean hasAuctions() {
        for (Document doc : DB.getAllDocuments("auctions")) {
            if (doc.get("owner").equals(getBukkitPlayer().getUniqueId().toString()))
                return true;
        }

        return false;
    }

}
