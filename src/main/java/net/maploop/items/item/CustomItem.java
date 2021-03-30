package net.maploop.items.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.maploop.items.Items;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.util.Attribute;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public abstract class CustomItem {

    private final int id;
    private final Rarity rarity;
    private final String name;
    private final Material material;
    private List<String> defaultLore;
    private boolean stackable = false;
    private boolean oneTimeUse = false;
    private boolean hasActive = false;
    private boolean reforgeable = false;
    private final ItemType itemType;
    private int manaCost = 0;
    private List<ItemAbility> abilities = new ArrayList<>();
    private String url;
    private final int durability;
    private final boolean glowing;

    private int damage = 0;
    private int strength = 0;
    private int crit = 0;
    private int intelligence = 0;
    private int health = 0;


    public CustomItem(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing) {
        this.id = id;
        this.rarity = rarity;
        this.name = name;
        this.material = material;
        this.stackable = stackable;
        this.oneTimeUse = oneTimeUse;
        this.hasActive = hasActive;
        this.abilities = abilities;
        this.manaCost = manaCost;
        this.reforgeable = reforgeable;
        this.itemType = itemType;
        this.durability = durability;
        this.glowing = glowing;
    }

    public CustomItem(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, String url, boolean glowing) {
        this.id = id;
        this.rarity = rarity;
        this.name = name;
        this.material = material;
        this.stackable = stackable;
        this.oneTimeUse = oneTimeUse;
        this.hasActive = hasActive;
        this.abilities = abilities;
        this.manaCost = manaCost;
        this.reforgeable = reforgeable;
        this.itemType = itemType;
        this.url = url;
        this.durability = durability;
        this.glowing = glowing;
    }

    public CustomItem(int id, Rarity rarity, String name, Material material, int durability, boolean stackable, boolean oneTimeUse, boolean hasActive, List<ItemAbility> abilities, int manaCost, boolean reforgeable, ItemType itemType, boolean glowing, int damage, int strength, int crit_damage, int intelligence, int health) {
        this.id = id;
        this.rarity = rarity;
        this.name = name;
        this.material = material;
        this.stackable = stackable;
        this.oneTimeUse = oneTimeUse;
        this.hasActive = hasActive;
        this.abilities = abilities;
        this.manaCost = manaCost;
        this.reforgeable = reforgeable;
        this.itemType = itemType;
        this.durability = durability;
        this.glowing = glowing;
        this.health = health;
        this.strength = strength;
        this.damage = damage;
        this.crit = crit_damage;
        this.intelligence = intelligence;
    }

    public ItemType getType() {
        return this.itemType;
    }

    public List<String> getLore(ItemStack item) {
        List<String> lore = new ArrayList<>();
        if (this.rarity == Rarity.UNFINISHED) {
            lore.add(ChatColor.RED + "This item is WIP");
            lore.add(ChatColor.RED + "It has not been completed");
            lore.add(ChatColor.RED + "and might be broken");
        }
        if(damage != 0) {
            lore.add("§7Damage: §c+" + damage);
        }
        if(strength != 0) {
            lore.add("§7Strength: §c+" + strength);
        }
        if(crit != 0) {
            lore.add("§7Crit Damage: §c+" + crit);
        }
        if(health != 0) {
            lore.add("§7Health: §a+" + health);
        }
        if(intelligence != 0) {
            lore.add("§7Intelligence: §a+" + intelligence);
        }

        getSpecificLorePrefix(lore, item);
        if (abilities != null) {
            for (ItemAbility ability : this.abilities) {
                lore.addAll(ability.toLore());
            }
        }
        if (this.manaCost != 0) {
            lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        }
        if (this.reforgeable) {
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "This item can be reforged!");
        }
        getSpecificLoreSuffix(lore, item);
        if (!this.reforgeable) {
            lore.add("");
        }
        if(this.rarity == Rarity.SKYBLOCK_MENU) {
            lore.add("§eClick to open!");

            return lore;
        }

        lore.add("" + this.rarity.getColor() + ChatColor.BOLD + this.rarity.toString().replaceAll("_", " ") + " " + itemType.getValue());
        return lore;
    }

    public void updateLore(ItemStack item) {
        if (item == null)
            return;
        ItemUtilities.loreItem(item, getLore(item));
    }

    public void changeMaterial(ItemStack item, Material material) {
        if (item == null)
            return;
        item.setType(material);
    }

    public void enforceStackability(ItemStack item) {
        if (item == null)
            return;
        if (!(this.stackable)) {
            ItemUtilities.storeStringInItem(item, UUID.randomUUID().toString(), "UUID");
        }
    }

    public void applyTexture(ItemStack item) {
        if (item == null) return;

        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        item.setItemMeta(itemMeta);
    }

    public void init(ItemStack item) {
        setDurability(item);
        applyTexture(item);
    }

    public void setDurability(ItemStack item) {
        item.setDurability((short) durability);
    }

    public void onItemUse(Player player, ItemStack item) {
        if (this.oneTimeUse && player.getGameMode() != GameMode.CREATIVE)
            destroy(item, 1);
    }
    
    public void onSwapAction(Player player, PlayerItemHeldEvent event, ItemStack item) {}
    
    public abstract void onItemStackCreate(ItemStack paramItemStack);

    public abstract void getSpecificLorePrefix(List<String> paramList, ItemStack paramItemStack);

    public abstract void getSpecificLoreSuffix(List<String> paramList, ItemStack paramItemStack);

    public abstract void leftClickAirAction(Player player, ItemStack item);

    public abstract void leftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item);

    public abstract void rightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item);

    public abstract void rightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item);

    public abstract void shiftLeftClickAirAction(Player player, ItemStack item);

    public abstract void shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item);

    public abstract void shiftRightClickAirAction(Player player, PlayerInteractEvent event, ItemStack item);

    public abstract void shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block paramBlock, ItemStack item);

    public abstract void middleClickAction(Player player, ItemStack item);

    public abstract void hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity paramEntity, ItemStack item);

    public abstract void breakBlockAction(Player player, BlockBreakEvent event, Block paramBlock, ItemStack item);

    public abstract void clickedInInventoryAction(Player player, InventoryClickEvent event);

    public abstract void activeEffect(Player player, ItemStack item);

    public void playerFishAction(Player player, PlayerFishEvent event, ItemStack item) {}

    public void playerShootAction(Player player, EntityShootBowEvent event, ItemStack item) {}

    public int getID() {
        return this.id;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public boolean isStackable() {
        return this.stackable;
    }

    public boolean hasActiveEffect() {
        return this.hasActive;
    }

    public short getDurability() {
        return (short) this.durability;
    }

    public int getHealth() {
        return health;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStrength() {
        return this.strength;
    }


    public int getCrit() {
        return crit;
    }

    public static ItemStack fromString(Items main, String name, int stackSize) {

        CustomItem item;
        String effectiveName = name;
        if (ItemUtilities.isInteger(name)) {
            item = SBItems.items.get(SBItems.itemIDs.get(Integer.parseInt(name)));
            effectiveName = SBItems.itemIDs.get(Integer.parseInt(name));
            Bukkit.getLogger().info(item.getName());
        } else {
            item = SBItems.items.get(name);
        }
        if (item == null)
            return null;
        ItemStack newItemStack = new ItemStack(item.getMaterial());
        ItemUtilities.nameItem(newItemStack, item.getRarity().getColor() + item.getName());

        ItemStack step1 = ItemUtilities.storeStringInItem(newItemStack, "true", "is-SB");

        ItemStack step2 = ItemUtilities.storeStringInItem(step1, effectiveName, "SB-name");

        ItemStack step3 = ItemUtilities.storeIntInItem(step2, item.getID(), "SB-ID");

        ItemStack step4 = ItemUtilities.storeStringInItem(step3, item.getRarity().toString(), "Rarity");

        ItemStack step5 = ItemUtilities.storeIntInItem(step4,  item.getStrength(), Attribute.STRENGTH.toString());
        ItemStack step6 = ItemUtilities.storeIntInItem(step5,  item.getCrit(), Attribute.CRIT_DAMAGE.toString());
        ItemStack step7 = ItemUtilities.storeIntInItem(step6,  item.getIntelligence(), Attribute.INTELLIGENCE.toString());
        ItemStack step8 = ItemUtilities.storeIntInItem(step7,  item.getHealth(), Attribute.HEALTH.toString());

        item.enforceStackability(step4);
        item.onItemStackCreate(step4);
        ItemUtilities.loreItem(step4, item.getLore(step4));

        if(!item.isStackable()) {
            ItemStack optionalStep = ItemUtilities.storeStringInItem(step4, UUID.randomUUID().toString(), "UUID");

            ItemMeta meta = optionalStep.getItemMeta();
            if(item.glowing) {
                meta.addEnchant(Enchantment.LUCK, 1, false);
            }
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            optionalStep.setDurability(item.getDurability());
            optionalStep.setItemMeta(meta);

            return optionalStep;
        }

        ItemMeta meta = step4.getItemMeta();
        if(item.glowing) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
        }
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        step4.setDurability(item.getDurability());
        step4.setItemMeta(meta);
        return step4;
    }

    public static void destroy(ItemStack item, int quantity) {
        if (item.getAmount() <= quantity) {
            item.setAmount(0);
        } else {
            item.setAmount(item.getAmount() - quantity);
        }
    }
}