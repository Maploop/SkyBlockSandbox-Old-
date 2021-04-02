package net.maploop.items.enums;

public enum EnchantmentType {
    SHARPNESS("Sharpness"), SMITE("Smite"), BANE_OF_ARTHROPODS("Bane of Arthropods");

    private String friendlyName;

    EnchantmentType(String friendlyName) {

    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
