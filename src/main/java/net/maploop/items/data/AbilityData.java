package net.maploop.items.data;

import jdk.nashorn.internal.objects.annotations.Getter;
import net.maploop.items.item.ItemUtilities;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.security.SecureRandom;
import java.util.UUID;

public class AbilityData {
    public static ItemStack setData(EnumAbilityData dataType, ItemStack item, Object data, int index) {
        if(index > 5)
            throw new NullPointerException("Ability index can't be higher than 5!");

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound attributes = (tag.getCompound("ExtraAttributes") != null ? tag.getCompound("ExtraAttributes") : new NBTTagCompound());
        NBTTagCompound ability = (attributes.getCompound("Abilities") != null ? attributes.getCompound("Abilities") : new NBTTagCompound());
        NBTTagCompound abilitySlot = (ability.getCompound("Ability_" + index) != null ? ability.getCompound("Ability_" + index) : new NBTTagCompound());
        abilitySlot.setString(dataType.getA(), data.toString());
        ability.set("Ability_" + index, abilitySlot);
        abilitySlot.setString("id", UUID.randomUUID().toString());

        attributes.set("Abilities", ability);
        tag.set("ExtraAttributes", attributes);
        nmsItem.setTag(tag);

        return ItemUtilities.storeIntInItem(CraftItemStack.asBukkitCopy(nmsItem), 1, "hasAbility");
    }

    public static boolean hasAbility(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound attributes = (tag.getCompound("ExtraAttributes") != null ? tag.getCompound("ExtraAttributes") : new NBTTagCompound());

        return attributes.hasKey("hasAbility");
    }

    public static Object retrieveData(EnumAbilityData dataType, ItemStack item, int index) {
        if(index > 5)
            throw new NullPointerException("Ability index can't be higher than 5!");

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound attributes = (tag.getCompound("ExtraAttributes") != null ? tag.getCompound("ExtraAttributes") : new NBTTagCompound());
        NBTTagCompound ability = (attributes.getCompound("Abilities") != null ? attributes.getCompound("Abilities") : new NBTTagCompound());
        NBTTagCompound abilitySlot = (ability.getCompound("Ability_" + index) != null ? ability.getCompound("Ability_" + index) : new NBTTagCompound());

        return abilitySlot.getString(dataType.getA());
    }

    public static boolean hasAbility(ItemStack item, int index) {
        if(index > 5)
            throw new NullPointerException("Ability index can't be higher than 5!");
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound attributes = (tag.getCompound("ExtraAttributes") != null ? tag.getCompound("ExtraAttributes") : new NBTTagCompound());
        NBTTagCompound ability = (attributes.getCompound("Abilities") != null ? attributes.getCompound("Abilities") : new NBTTagCompound());
        NBTTagCompound abilitySlot = (ability.getCompound("Ability_" + index) != null ? ability.getCompound("Ability_" + index) : new NBTTagCompound());

        return abilitySlot.hasKey("id");
    }

    public static ItemStack removeAbility(ItemStack item, int index) {
        if(index > 5)
            throw new NullPointerException("Ability index can't be higher than 5!");
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagCompound attributes = (tag.getCompound("ExtraAttributes") != null ? tag.getCompound("ExtraAttributes") : new NBTTagCompound());
        NBTTagCompound ability = (attributes.getCompound("Abilities") != null ? attributes.getCompound("Abilities") : new NBTTagCompound());
        ability.remove("Ability_" + index);

        attributes.set("Abilities", ability);
        tag.set("ExtraAttributes", attributes);
        nmsItem.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static ItemStack addAbilityFunction(ItemStack item, int index, FunctionType type, String command) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound originTag = (nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound());
        NBTTagCompound attributes = (originTag.getCompound("ExtraAttributes") != null ? originTag.getCompound("ExtraAttributes") : new NBTTagCompound());
        NBTTagCompound ability = (attributes.getCompound("Abilities") != null ? attributes.getCompound("Abilities") : new NBTTagCompound());
        NBTTagCompound abilitySlot = (ability.getCompound("Ability_" + index) != null ? ability.getCompound("Ability_" + index) : new NBTTagCompound());
        NBTTagCompound functions = abilitySlot.getCompound("functions");
        functions.setString(type.getId(), command);

        abilitySlot.set("functions", functions);
        ability.set("Ability_" + index, abilitySlot);
        attributes.set("Abilities", ability);
        originTag.set("ExtraAttributes", attributes);
        nmsItem.setTag(originTag);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public enum FunctionType {
        MESSAGE("message");

        private final String id;

        public String getId() {
            return this.id;
        }

        FunctionType(String s) {
            this.id = s;
        }
    }
}
