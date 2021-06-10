package net.maploop.items.extras;

import net.maploop.items.enums.Enchant;

public class Enchantment {
    private Enchant type;
    private int lvl;

    public Enchantment(Enchant type, int lvl) {
        this.type = type;
        this.lvl = lvl;
    }

    public void setType(Enchant type) {
        this.type = type;
    }

    public Enchant getType() {
        return type;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
}
