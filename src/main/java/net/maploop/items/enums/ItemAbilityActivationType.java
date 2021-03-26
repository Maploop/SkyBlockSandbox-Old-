package net.maploop.items.enums;

public enum ItemAbilityActivationType {
    RIGHT_CLICK("§e§lRIGHT CLICK"),
    LEFT_CLICK("§e§lLEFT CLICK"),
    SNEAK("§e§lSNEAK"),
    PASSIVE("");

    private String s;

    ItemAbilityActivationType(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
}
