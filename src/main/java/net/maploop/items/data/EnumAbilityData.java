package net.maploop.items.data;

public enum EnumAbilityData {
    NAME("name", 1),
    COOLDOWN("cooldown", 2),
    DAMAGE("damage", 3),
    MANA_COST("mana_cost", 4),
    FUNCTION("function", 5),
    BASE_ABILITY("base_ability", 6);

    private final String a;
    private final int b;

    public String getA() {
        return this.a;
    }

    public int getB() {
        return this.b;
    }

    EnumAbilityData(String a, int b) {
        this.a = a;
        this.b = b;
    }
}
