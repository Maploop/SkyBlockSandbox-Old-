package net.maploop.items.item;

import net.maploop.items.Items;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SBItems {
    public static String uniqueId;
    public static Map<UUID, String> id = new HashMap<>();
    public static Map<UUID, String> cooldownmn = new HashMap<>();

    public static Map<String, CustomItem> items = new HashMap<>();
    public static Map<Integer, String> itemIDs = new HashMap<>();
    public static Set<ItemStack> categoryWeapon = new HashSet<>();
    public static Set<ItemStack> categoryArmor = new HashSet<>();
    public static Set<ItemStack> categoryAccessory = new HashSet<>();
    public static Set<ItemStack> categoryTools = new HashSet<>();
    public static Set<ItemStack> categoryOther = new HashSet<>();

    public static HashMap<UUID, ArmorStand> isPlaced = new HashMap<>();
    public static HashMap<ArmorStand, Long> timer = new HashMap<>();

    public static void putItem(String name, CustomItem item) {
        items.put(name, item);
        itemIDs.put(item.getID(), name);

        switch (item.getType()) {
            case PICKAXE:
            case AXE:
            case HOE:
            case WAND: {
                categoryTools.add(CustomItem.fromString(Items.getInstance(), name, 1));
                break;
            }
            case SWORD:
            case DUNGEON_SWORD:
            case DUNGEON_BOW:
            case BOW: {
                categoryWeapon.add(CustomItem.fromString(Items.getInstance(), name, 1));
                break;
            }
            case HELMET:
            case CHESPLATE:
            case LEGGINGS:
            case BOOTS: {
                categoryArmor.add(CustomItem.fromString(Items.getInstance(), name, 1));
                break;
            }
            case ACCESSORY: {
                categoryAccessory.add(CustomItem.fromString(Items.getInstance(), name, 1));
                break;
            }
            case ITEM:
            case DUNGEON_ITEM: {
                categoryOther.add(CustomItem.fromString(Items.getInstance(), name, 1));
                break;
            }
        }
    }

    public static List<ItemStack> getItems() {
        List<ItemStack> gottenItems = new ArrayList<>();

        for(String key : items.keySet()) {
            ItemStack item = CustomItem.fromString(Items.getInstance(), key, 1);
            gottenItems.add(item);
        }
        gottenItems.remove(CustomItem.fromString(Items.getInstance(), "skyblock_menu", 1));

        return gottenItems;
    }

}