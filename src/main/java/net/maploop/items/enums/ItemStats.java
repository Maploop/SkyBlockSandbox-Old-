package net.maploop.items.enums;

public enum ItemStats {
    SPEED("§7Speed: §a+"),
    DAMAGE("§7Damage: §c+"),
    STRENGTH("§7Strength: §c+"),
    HEALTH("§7Health: §a+"),
    DEFENSE("§7Defense: §a+"),
    INTELLIGENCE("§7Intelligence: §a+"),
    CRIT_CHANCE("§7Crit Chance: §c+"),
    CRIT_DAMAGE("§7Crit Damage: §c+"),
    MINIG_SPEED("§7Mining Speed: §a+"),
    BONUS_ATTACK_SPEED("§7Bonus Attack Speed: §a+"),
    SEA_CHANCE("§7Sea Creature Chance: §a+"),
    MAGIC_FIND("§7Magic Find: §a+"),
    PET_LUCK("§7Pet Luck: §a+"),
    FEROCITY("§7Ferocity: §c+"),
    ABILITY_DAMAGE("§7Ability Damage: §c+"),
    MINING_FORTUNE("§7Mining Fortune: §a+"),
    FARMING_FORTUNE("§7Farming Fortune: §a+"),
    FORAGING_FORTUNE("§7Foraging Fortune: §a+"),
    GEAR_SCORE("§7Gear Score: §d");

    String stat;

    public String getDisplayname() {
        return this.stat;
    }

    ItemStats(String stat) {
        this.stat = stat;
    }
}
