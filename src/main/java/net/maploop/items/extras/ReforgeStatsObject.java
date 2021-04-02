package net.maploop.items.extras;

public class ReforgeStatsObject {
    private int crit_damage;
    private int crit_chance;
    private int strength;
    private int intelligence;
    private int ferocity;
    private int health;
    private int defense;

    public ReforgeStatsObject(int crit_chance, int crit_damage, int strength, int intelligence, int ferocity, int health, int defense) {
        this.crit_chance = crit_chance;
        this.crit_damage = crit_damage;
        this.strength = strength;
        this.intelligence = intelligence;
        this.ferocity = ferocity;
        this.health = health;
        this.defense = defense;
    }

    public int getHealth() {
        return health;
    }

    public int getCrit_chance() {
        return crit_chance;
    }

    public int getCrit_damage() {
        return crit_damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getFerocity() {
        return ferocity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStrength() {
        return strength;
    }
}
