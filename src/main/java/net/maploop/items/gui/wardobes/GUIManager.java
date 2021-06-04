package net.maploop.items.gui.wardobes;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.maploop.items.gui.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public abstract class GUIManager implements InventoryHolder {

    protected PlayerMenuUtility playerMenuUtility;
    protected ItemStack itemStack;
    protected Inventory inventory;
    protected String uuid;
    protected ItemStack FILLER_GLASS = makeItem(Material.STAINED_GLASS_PANE, " ", 1, 15);

    public GUIManager(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getTitle();

    public abstract int getSize();

    public abstract void hadleMenu(InventoryClickEvent event);

    public abstract void setItems();

    public abstract void closeMenu(InventoryCloseEvent event);

    public void open() {
        inventory = Bukkit.createInventory(this, getSize(), getTitle());
        this.setItems();
        playerMenuUtility.getOwner().openInventory(inventory);
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setFilter() {
        for (int slots = 0; slots < getSize(); slots++) {
            if (inventory.getItem(slots) == null) {
                inventory.setItem(slots, FILLER_GLASS);
            }
        }
    }

    public ItemStack makeItem(Material material, String displayName, int amount, int durability, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeItem(Material material, String displayName, int amount, int durability, String lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        String[] lore1 = lore.split("\n");
        meta.setLore(Arrays.asList(lore1));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeItem(Material material, String displayName, int amount, int durability, String lore, boolean glowing) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if(glowing) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        String[] lore1 = lore.split("\n");
        meta.setLore(Arrays.asList(lore1));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeItem(Material material, String displayName, int amount, int durability, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        String[] lore1 = lore.toArray(new String[0]);
        meta.setLore(Arrays.asList(lore1));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeAdvancedItem(Material material, String displayName, int amount, int durability, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack makeSkullItem(String displayname, String owner, int amount, String lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(owner);
        String[] lore1 = lore.split("\n");
        meta.setLore(Arrays.asList(lore1));
        meta.setDisplayName(displayname);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack makeCustomSkullItem(String url, String displayname, int amount, String lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        if (url.isEmpty()) return item;

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
        itemMeta.setDisplayName(displayname);
        String[] lore1 = lore.split("\n");
        itemMeta.setLore(Arrays.asList(lore1));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack makeTexturedSkullItem(String texture, String displayname, int amount, String... lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        if (texture == null) return item;

        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemMeta.setDisplayName(displayname);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }

    public void fillBorder() {
        ItemStack item = FILLER_GLASS;
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        // Fill top
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, item);
        }

        // Fill bottom
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }

        // Fill sides
        for (int i = 2; i <= rows - 1; i++) {
            int[] slots = new int[]{i * 9 - 1, (i - 1) * 9};
            inventory.setItem(slots[0], item);
            inventory.setItem(slots[1], item);
        }
    }

    public void fillBottom() {
        ItemStack item = FILLER_GLASS;
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }
    }

    public void fillBottom(ItemStack is) {
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, is);
        }
    }

    public void fillSides() {
        ItemStack item = FILLER_GLASS;
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        for (int i = 2; i <= rows - 1; i++) {
            int[] slots = new int[]{i * 9 - 1, i * 9};
            inventory.setItem(slots[0], item);
            inventory.setItem(slots[1], item);
        }
    }

    public void fillSides(ItemStack item) {
        int size = inventory.getSize();
        int rows = (size + 1) / 9;

        for (int i = 2; i <= rows - 1; i++) {
            int[] slots = new int[]{i * 9 - 1, (i - 1) * 9};
            inventory.setItem(slots[0], item);
            inventory.setItem(slots[1], item);
        }
    }

    public ItemStack getEmpty(final Integer slot) {
        final ItemStack item = new ItemStack(Material.INK_SACK, 1, (short)8);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Slot " + slot + "&7: &cEmpty"));
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7This wardrobe slot contains no"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7armor."));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getEquiped(final Integer slot) {
        final ItemStack item = new ItemStack(Material.INK_SACK, 1, (short)10);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Slot " + slot + "&7: &aEquipped"));
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7This wardrobe slot contains your"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7current armor set."));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getSlot(final Integer slot, final Integer color, final Integer armorType) {
        ItemStack item = null;
        switch (color) {
            case 1: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                break;
            }
            case 2: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)1);
                break;
            }
            case 3: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
                break;
            }
            case 4: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                break;
            }
            case 5: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
                break;
            }
            case 6: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)3);
                break;
            }
            case 7: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
                break;
            }
            case 8: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)6);
                break;
            }
            case 9: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)10);
                break;
            }
            default: {
                item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
                break;
            }
        }
        String type = null;
        switch (armorType) {
            case 1: {
                type = "Helmet";
                break;
            }
            case 2: {
                type = "Chestplate";
                break;
            }
            case 3: {
                type = "Leggings";
                break;
            }
            case 4: {
                type = "Boots";
                break;
            }
            default: {
                type = "Armor Piece";
                break;
            }
        }
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aSlot " + slot + "&a " + type));
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Place a pair of " + type + "&7 here to"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7add them to the armor set."));
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
}
