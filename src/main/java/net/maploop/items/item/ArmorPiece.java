package net.maploop.items.item;

import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import org.bukkit.Material;

import java.util.List;

public abstract class ArmorPiece extends CustomItem {
    public ArmorPiece(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing, int damage, int strength, int crit_damage, int intelligence, int health, int defense) {
        super(id, rarity, name, material, durability, stackable, oneTimeUse, hasActive, abilities, manaCost, reforgeable, itemType, glowing, damage, strength, crit_damage, intelligence, health, defense);
    }
}
