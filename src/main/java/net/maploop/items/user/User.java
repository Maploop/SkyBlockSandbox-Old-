package net.maploop.items.user;

import jdk.nashorn.internal.ir.Block;
import net.maploop.items.Items;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class User {
    private final Player user;

    public User(Player player) {
        this.user = player;
    }

    private Map<Enchantment, Integer> ench_opt_1;
    private Map<Enchantment, Integer> ench_opt_2;
    private Map<Enchantment, Integer> ench_opt_3;

    private double strength;
    private double crit_damage;
    private double crit_chance;
    private double defense;

    public Player getUser() {
        return user;
    }

    private double max_intelligence;
    private double max_health;

    private String lastSkill;
    private double gainedXP;
    private Block block;

    private double intelligence;
    private double health;

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

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getCrit_damage() {
        return crit_damage;
    }

    public void setCrit_damage(double crit_damage) {
        this.crit_damage = crit_damage;
    }

    public double getCrit_chance() {
        return crit_chance;
    }

    public void setCrit_chance(double crit_chance) {
        this.crit_chance = crit_chance;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getMax_intelligence() {
        return max_intelligence;
    }

    public void setMax_intelligence(double max_intelligence) {
        this.max_intelligence = max_intelligence;
    }

    public double getMax_health() {
        return max_health;
    }

    public void setMax_health(double max_health) {
        this.max_health = max_health;
    }

    public String getLastSkill() {
        return lastSkill;
    }

    public void setLastSkill(String lastSkill) {
        this.lastSkill = lastSkill;
    }

    public double getGainedXP() {
        return gainedXP;
    }

    public void setGainedXP(double gainedXP) {
        this.gainedXP = gainedXP;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = intelligence;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
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
}
