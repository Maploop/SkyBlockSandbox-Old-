package net.maploop.items.gui.itemCreator;

import net.maploop.items.Items;
import net.maploop.items.api.SignGUI;
import net.maploop.items.api.SignGUIUpdateEvent;
import net.maploop.items.gui.AnvilGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.util.Attribute;
import net.maploop.items.util.IUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatsEditorGUI extends GUI {
    /*
    TODO
      - Add damage, critchance and critdamage editing to the GUI.
     */

    public StatsEditorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public static Set<Player> healthChange = new HashSet<>();
    public static Set<Player> intelligenceChange = new HashSet<>();
    public static Set<Player> defenseChange = new HashSet<>();
    public static Set<Player> strengthChange = new HashSet<>();
    public static Set<Player> damage = new HashSet<>();

    @Override
    public String getTitle() {
        return "Edit item stats";
    }

    @Override
    public int getSize() {
        return 9 * 6;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case ARROW: {
                new ItemCreatorGUI(playerMenuUtility).open();
                break;
            }
            case GOLDEN_APPLE: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null && Integer.parseInt(event.getName()) >= 0) {
                            ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                            ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getName()), Attribute.HEALTH.toString());
                            ItemMeta hmeta = h.getItemMeta();
                            List<String> lore;
                            if (hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                            else lore = new ArrayList<>();
                            lore.add("§7Health: §a+" + event.getName());
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
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();
                break;
            }
            case EYE_OF_ENDER: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                            ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getName()), Attribute.INTELLIGENCE.toString());
                            ItemMeta hmeta = h.getItemMeta();
                            List<String> lore;
                            if (hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                            else lore = new ArrayList<>();
                            lore.add("§7Intelligence: §a+" + event.getName());
                            hmeta.setLore(lore);
                            h.setItemMeta(hmeta);

                            player.setItemInHand(h);

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
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
            case IRON_CHESTPLATE: {

                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                            ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getName()), Attribute.DEFENSE.toString());
                            ItemMeta hmeta = h.getItemMeta();
                            List<String> lore;
                            if (hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                            else lore = new ArrayList<>();
                            lore.add("§7Defense: §a+" + event.getName());
                            hmeta.setLore(lore);
                            h.setItemMeta(hmeta);

                            player.setItemInHand(h);

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
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
            case IRON_SWORD: {

                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                            ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getName()), Attribute.STRENGTH.toString());
                            ItemMeta hmeta = h.getItemMeta();
                            List<String> lore;
                            if (hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                            else lore = new ArrayList<>();
                            lore.add("§7Strength: §c+" + event.getName());
                            hmeta.setLore(lore);
                            h.setItemMeta(hmeta);

                            player.setItemInHand(h);

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
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
            case BLAZE_POWDER: {

                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if (ItemUtilities.isInteger(event.getName()) && event.getName() != null) {
                            ItemStack a = ItemUtilities.storeStringInItem(player.getItemInHand(), "true", "is-SB");
                            ItemStack h = ItemUtilities.storeIntInItem(a, Integer.parseInt(event.getName()), Attribute.DAMAGE.toString());
                            ItemMeta hmeta = h.getItemMeta();
                            List<String> lore;
                            if (hmeta.hasLore()) lore = new ArrayList<>(hmeta.getLore());
                            else lore = new ArrayList<>();
                            lore.add("§7Damage: §c+" + event.getName());
                            hmeta.setLore(lore);
                            h.setItemMeta(hmeta);

                            player.setItemInHand(h);

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
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Enter value", 1, 0));
                gui.open();

                break;
            }
        }
    }

    @Override
    public void setItems() {
        setFilter();
        inventory.setItem(49, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an Item"));

        inventory.setItem(13, makeItem(Material.EYE_OF_ENDER, "§aSet Intelligence", 1, 0, IUtil.colorize("&7Edit the amount of &b✎ Intelligence\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(15, makeItem(Material.IRON_CHESTPLATE, "§aSet Defense", 1, 0, IUtil.colorize("&7Edit the amount of &a❈ Defense\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(31, makeItem(Material.IRON_SWORD, "§aSet Strength", 1, 0, IUtil.colorize("&7Edit the amount of &c❁ Strength\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(11, makeItem(Material.GOLDEN_APPLE, "§aSet Health", 1, 0, IUtil.colorize("&7Edit the amount of &c❤ Health\n&7your item has!\n\n&eClick to set!")));
        inventory.setItem(29, makeItem(Material.BLAZE_POWDER,"§aSet Damage",1,0,IUtil.colorize("&7Edit the amount of &c❁ Damage\n&7your item has!\n\n&eClick to set!")));
    }

    private void invalidNumberError(AnvilGUI.AnvilClickEvent event, Player player) {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\":\"§cThat's not a valid number!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§fYour input: §e" + event.getName() + "\"}}");
        PacketPlayOutChat c = new PacketPlayOutChat(comp);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(c);

        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
        player.closeInventory();
    }
}
