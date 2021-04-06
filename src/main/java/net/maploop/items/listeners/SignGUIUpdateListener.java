package net.maploop.items.listeners;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUIUpdateEvent;
import net.maploop.items.gui.ItemsGUI;
import net.maploop.items.gui.MinecraftItemsGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.gui.itemCreator.StatsEditorGUI;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.Attribute;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SignGUIUpdateListener implements Listener {
    @EventHandler
    public void onUpdate(SignGUIUpdateEvent event) {
        Player player = event.getPlayer();
        if(ItemsGUI.searching.contains(player)) {
            if(event.getSignText()[0] == null) {
                player.sendMessage("§cInvalid search!");
                return;
            }
            ItemsGUI.search.put(player, event.getSignText()[0]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    new ItemsGUI(new PlayerMenuUtility(player)).open();
                    ItemsGUI.searching.remove(player);
                }
            }.runTaskLater(Items.getInstance(), 2);
            return;
        }
        if(MinecraftItemsGUI.mcSearching.contains(player)) {
            if(event.getSignText()[0] == null) {
                player.sendMessage("§cInvalid search!");
                return;
            }
            MinecraftItemsGUI.mcSearch.put(player, event.getSignText()[0]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                    MinecraftItemsGUI.mcSearching.remove(player);
                }
            }.runTaskLater(Items.getInstance(), 2);
        }
        /** For item stat changes:*/
        else if (StatsEditorGUI.healthChange.contains(player)) {
            if(ItemUtilities.isInteger(event.getSignText()[0]) && event.getSignText()[0] != null) {
                ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getSignText()[0]), Attribute.HEALTH.toString());
                ItemMeta hmeta = h.getItemMeta();
                List<String> lore;
                if(hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                else lore = new ArrayList<>();
                lore.add("§7Health: §a+" + event.getSignText()[0]);
                hmeta.setLore(lore);
                h.setItemMeta(hmeta);

                player.setItemInHand(h);
                StatsEditorGUI.healthChange.remove(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                    }
                }.runTaskLater(Items.getInstance(), 2);
            } else {
                invalidNumberError(event, player);
            }
        } else if (StatsEditorGUI.intelligenceChange.contains(player)) {
            if(ItemUtilities.isInteger(event.getSignText()[0]) && event.getSignText()[0] != null) {
                ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getSignText()[0]), Attribute.INTELLIGENCE.toString());
                ItemMeta hmeta = h.getItemMeta();
                List<String> lore;
                if(hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                else lore = new ArrayList<>();
                lore.add("§7Intelligence: §a+" + event.getSignText()[0]);
                hmeta.setLore(lore);
                h.setItemMeta(hmeta);

                player.setItemInHand(h);
                StatsEditorGUI.intelligenceChange.remove(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                    }
                }.runTaskLater(Items.getInstance(), 2);
            } else {
                invalidNumberError(event, player);
            }
        } else if (StatsEditorGUI.defenseChange.contains(player)) {
            if(ItemUtilities.isInteger(event.getSignText()[0]) && event.getSignText()[0] != null) {
                ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getSignText()[0]), Attribute.DEFENSE.toString());
                ItemMeta hmeta = h.getItemMeta();
                List<String> lore;
                if(hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                else lore = new ArrayList<>();
                lore.add("§7Defense: §a+" + event.getSignText()[0]);
                hmeta.setLore(lore);
                h.setItemMeta(hmeta);

                player.setItemInHand(h);
                StatsEditorGUI.defenseChange.remove(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                    }
                }.runTaskLater(Items.getInstance(), 2);
            } else {
                invalidNumberError(event, player);
            }
        } else if (StatsEditorGUI.strengthChange.contains(player)) {
            if(ItemUtilities.isInteger(event.getSignText()[0]) && event.getSignText()[0] != null) {
                ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getSignText()[0]), Attribute.STRENGTH.toString());
                ItemMeta hmeta = h.getItemMeta();
                List<String> lore;
                if(hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                else lore = new ArrayList<>();
                lore.add("§7Strength: §c+" + event.getSignText()[0]);
                hmeta.setLore(lore);
                h.setItemMeta(hmeta);

                player.setItemInHand(h);
                StatsEditorGUI.strengthChange.remove(player);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new StatsEditorGUI(new PlayerMenuUtility(player)).open();
                    }
                }.runTaskLater(Items.getInstance(), 2);
            } else {
                invalidNumberError(event, player);
            }
        }
    }

    private void invalidNumberError(SignGUIUpdateEvent event, Player player) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\":\"§cThat's not a valid number!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§fYour input: §e" + event.getSignText()[0] + "\"}}");
        PacketPlayOutChat c = new PacketPlayOutChat(comp);
        ((CraftPlayer)event.getPlayer()).getHandle().playerConnection.sendPacket(c);

        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
        player.closeInventory();
    }
}
