package net.maploop.items.extras;

import net.maploop.items.enums.EnchantmentType;

public class Enchantment {
    private EnchantmentType type;
    private int lvl;

    public Enchantment(EnchantmentType type, int lvl) {
        this.type = type;
        this.lvl = lvl;
    }

    public void setType(EnchantmentType type) {
        this.type = type;
    }

    public EnchantmentType getType() {
        return type;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
}
