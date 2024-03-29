package net.maploop.items.item;

import net.maploop.items.Items;
import net.maploop.items.enums.ActionSound;
import net.maploop.items.enums.Rarity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ItemUtilities {
    private static final Set<Material> TRANSPARENT = EnumSet.of(Material.AIR, Material.CARPET, Material.IRON_FENCE);

    private static Map<Player, Long> mostRecentSelect = new HashMap<>();

    public static String toLocString(Location location) {
        if (location.equals(null))
            return "";
        return location.getWorld().getName() + "," + (int) location.getX() + "," + (int) location.getY() + "," + (int) location.getZ();
    }

    public static Location fromLocString(String locString) {
        if (locString.equals(""))
            return null;
        String[] data = locString.split(",");
        return new Location(Bukkit.getWorld(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
    }

    public static ItemStack loreItem(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack nameItem(Material item, String name) {
        return nameItem(new ItemStack(item), name);
    }

    public static ItemStack nameItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static void repairItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable) {
            ((Damageable) meta).setHealth(((Damageable) meta).getMaxHealth());
            item.setItemMeta(meta);
        }
    }

    public static void warnPlayer(CommandSender sender, List<String> messages) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            playSound(ActionSound.ERROR, player);
        }
        for (String message : messages)
            sender.sendMessage(ChatColor.RESET + "" + ChatColor.RED + message);
    }

    public static void informPlayer(CommandSender sender, List<String> messages) {
        for (String message : messages)
            sender.sendMessage(ChatColor.RESET + "" + ChatColor.GRAY + message);
    }

    public static Block getBlockLookingAt(Player player) {
        return player.getTargetBlock(TRANSPARENT, 120);
    }

    public static void playSound(ActionSound sound, Player player) {
        switch (sound) {
            case OPEN:
                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
                break;
            case MODIFY:
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);
                break;
            case SELECT:
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                break;
            case CLICK:
                player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                break;
            case POP:
                player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                break;
            case BREAK:
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
                break;
            case ERROR:
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0.5F);
                break;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ItemStack storeStringInItem(ItemStack host, String string, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(host);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound data = tag.getCompound("ExtraAttributes");
        data.setString(key, string);
        tag.set("ExtraAttributes", data);
        nmsItem.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static String getStringFromItem(ItemStack host, String key) {
        if(host != null) {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(host);
            NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
            NBTTagCompound data = tag.getCompound("ExtraAttributes");
            if(data == null) return  null;

            return data.getString(key);
        }
        return null;
    }

    public static ItemStack storeIntInItem(ItemStack host, Integer i, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(host);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound data = tag.getCompound("ExtraAttributes");
        data.setInt(key, i);
        tag.set("ExtraAttributes", data);
        nmsItem.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static Integer getIntFromItem(ItemStack host, String key) {
        if(host != null) {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(host);
            NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
            NBTTagCompound data = tag.getCompound("ExtraAttributes");
            if(data == null) return  null;

            return data.getInt(key);
        }
        return null;
    }

    public static void scheduleTask(Runnable run, int i) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) Items.getInstance(), run, i);
    }

    public static boolean isSBItem(ItemStack item) {
        return (getStringFromItem(item, "is-SB") != null);
    }

    public static boolean isSBItem(ItemStack item, int id) {
        if (!isSBItem(item)) {
            return false;
        }
        return (getIntFromItem(item, "SB-ID") == id);
    }

    public static boolean isCustomItem(ItemStack stack) {
        if(getStringFromItem(stack, "is-SB") != null) return true;
        else {
            return false;
        }
    }

    public static CustomItem getSBItem(ItemStack item) {
        if (!isSBItem(item))
            return null;
        return SBItems.items.get(getStringFromItem(item, "SB-name"));
    }

    public static Rarity getRarity(ItemStack stack) {
        if(getStringFromItem(stack, "Rarity") == null) {
            return null;
        }
        return Rarity.valueOf(getStringFromItem(stack, "Rarity"));
    }

    public static boolean hasRarity(ItemStack stack) {
        return getStringFromItem(stack, "Rarity") != null;
    }

    public static CustomItem getItem(String name) {
        for (String key : SBItems.items.keySet()) {
            CustomItem SBItemm = SBItems.items.get(key);
            if (SBItemm.getName().equals(name))
                return SBItemm;
        }
        return null;
    }

    public static void activeEffects() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isSBItem(player.getInventory().getItemInHand())) {
                CustomItem item = getSBItem(player.getInventory().getItemInHand());
                if (item.hasActiveEffect())
                    item.activeEffect(player, player.getInventory().getItemInHand());
            }
        }
    }

    public static ItemStack searchFor(Inventory inv, int id) {
        for (ListIterator<ItemStack> listIterator = inv.iterator(); listIterator.hasNext(); ) {
            ItemStack item = listIterator.next();
            if (isSBItem(item, id))
                return item;
        }
        return null;
    }

    public static boolean enforceCooldown(Player player, String key, double seconds, ItemStack item, boolean throwError) {
        double time = System.currentTimeMillis() / 1000.0D;
        int lastTime = getIntFromItem(item, key);
        if (lastTime == 0) {
            player.setItemInHand(storeIntInItem(item, (int) time, key));
            return true;
        }
        if (time - seconds > lastTime) {
            player.setItemInHand(storeIntInItem(item, (int) time, key));
            return true;
        }
        int timeLeft = (int) time - lastTime;
        timeLeft = (int) seconds - timeLeft;
        if (throwError)
            warnPlayer((CommandSender) player, Collections.singletonList("This ability is on cooldown for " + timeLeft + "s."));
        return false;
    }
    public static boolean enforceCooldown(Player player, HashMap<Player,Integer> key, double seconds, ItemStack item, boolean throwError) {
        double time = System.currentTimeMillis() / 1000.0D;
        int lastTime = key.get(player);
        if (lastTime == 0) {
            key.put(player, (int) time);
            return true;
        }
        if (time - seconds > lastTime) {
            key.put(player, (int) time);
            return true;
        }
        int timeLeft = (int) time - lastTime;
        timeLeft = (int) seconds - timeLeft;
        if (throwError)
            warnPlayer((CommandSender) player, Collections.singletonList("This ability is on cooldown for " + timeLeft + "s."));
        return false;
    }

    public static class AuctionData {
        public ItemStack put(ItemStack item, String key, String value) {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
            NBTTagCompound data = tag.getCompound("AuctionData");
            data.setString(key, value);
            data.setString("ah-item", "1");
            tag.set("AuctionData", data);
            nmsItem.setTag(tag);

            return CraftItemStack.asBukkitCopy(nmsItem);
        }

        public String getString(ItemStack item, String key) {
            if(item != null) {
                net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
                NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
                NBTTagCompound data = tag.getCompound("AuctionData");
                if(data == null) return  null;

                return data.getString(key);
            }
            return null;
        }

        public int getInt(ItemStack item, String key) {
            if(item != null) {
                net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
                NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
                NBTTagCompound data = tag.getCompound("AuctionData");
                if(data == null) return  0;

                return data.getInt(key);
            }
            return 0;
        }

        public boolean isAuctionedItem(ItemStack item) {
            if(item != null) {
                net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
                NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
                NBTTagCompound data = tag.getCompound("AuctionData");
                if(data == null) return false;

                return data.hasKey("ah-item");
            }
            return false;
        }

        public ItemStack remove(ItemStack stack) {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(stack);
            NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
            tag.remove("AuctionData");
            nmsItem.setTag(tag);

            return CraftItemStack.asBukkitCopy(nmsItem);
        }

        public ItemStack put(ItemStack item, String key, int value) {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
            NBTTagCompound data = tag.getCompound("AuctionData");
            data.setInt(key, value);
            data.setString("ah-item", "1");
            tag.set("AuctionData", data);
            nmsItem.setTag(tag);

            return CraftItemStack.asBukkitCopy(nmsItem);
        }
    }
}