package net.maploop.items.enums;

public enum DungeonClass {
    NONE("None"),
    HEALER("Healer"),
    MAGE("Mage"),
    BERSERK("Berserk"),
    ARCHER("Archer"),
    TANK("Tank");

    private final String s;
    DungeonClass(String s) {
        this.s = s;
    }

    public String getString() {
        return s;
    }
}
