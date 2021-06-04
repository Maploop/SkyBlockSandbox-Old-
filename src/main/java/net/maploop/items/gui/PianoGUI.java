package net.maploop.items.gui;

import net.maploop.items.util.IUtil;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PianoGUI extends GUI {
    public final static Set<Player> playingWithKeyboard = new HashSet<Player>();
    private final static Map<UUID, ItemStack[]> hotbar = new HashMap();

    public PianoGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Piano";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 4: {
                if(!playingWithKeyboard.contains(player)) {
                    System.out.println(playingWithKeyboard);
                    player.sendMessage("§aToggled on!");
                    player.playSound(player.getLocation(), Sound.CLICK, 1f, 1.5f);
                    IUtil.scheduleTask(() -> {
                        new PianoGUI(playerMenuUtility).open();
                        ItemStack i = makeItem(Material.QUARTZ_BLOCK, "§bKeyboard Key", 64, 0, "§7Press keys 1-8 in your keyboard\n§7to play a tone! Make sure to\n§7keep this hovered!");
                        player.getInventory().setItem(0, i);
                        player.getInventory().setItem(1, i);
                        player.getInventory().setItem(2, i);
                        player.getInventory().setItem(3, i);
                        player.getInventory().setItem(4, i);
                        player.getInventory().setItem(5, i);
                        player.getInventory().setItem(6, i);
                        player.getInventory().setItem(7, i);
                        playingWithKeyboard.add(player);
                    }, 2);
                    return;
                } else {
                    System.out.println(playingWithKeyboard);
                    player.sendMessage("§cToggled off!");
                    IUtil.scheduleTask(() -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 1f, 0.5f);
                        player.getInventory().setItem(0, new ItemStack(Material.AIR));
                        player.getInventory().setItem(1, new ItemStack(Material.AIR));
                        player.getInventory().setItem(2, new ItemStack(Material.AIR));
                        player.getInventory().setItem(3, new ItemStack(Material.AIR));
                        player.getInventory().setItem(4, new ItemStack(Material.AIR));
                        player.getInventory().setItem(5, new ItemStack(Material.AIR));
                        player.getInventory().setItem(6, new ItemStack(Material.AIR));
                        player.getInventory().setItem(7, new ItemStack(Material.AIR));
                        playingWithKeyboard.remove(player);

                        new PianoGUI(playerMenuUtility).open();
                    }, 2);
                }
                break;
            }
            case 31: {
                player.closeInventory();
                break;
            }
            case 10: {
                // C
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.707107f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            case 11: {
                // D
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.793701f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            case 12: {
                // E
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.890899f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            case 13: {
                // F
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 0.943874f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            case 14: {
                // G
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.059463f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            case 15: {
                // A
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.189207f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            case 16: {
                // B
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.334840f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            case 22: {
                // c #1
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 2.5f, 1.414214f);
                player.getWorld().playEffect(player.getEyeLocation(), Effect.NOTE, 1, 1);
                break;
            }
            default: {
                event.setCancelled(true);
                break;
            }
        }
    }

    @Override
    public void setItems() {
        Player player = playerMenuUtility.getOwner();

        setFilter();
        inventory.setItem(31, makeItem(Material.BARRIER, "§cClose", 1, 0));
        inventory.setItem(10, makeItem(Material.STAINED_CLAY, "§bC Note", 1, 3, "§7Click!"));
        inventory.setItem(11, makeItem(Material.STAINED_CLAY, "§9D Note", 1, 11, "§7Click!"));
        inventory.setItem(12, makeItem(Material.STAINED_CLAY, "§5E Note", 1, 10, "§7Click!"));
        inventory.setItem(13, makeItem(Material.STAINED_CLAY, "§2F Note", 1, 13, "§7Click!"));
        inventory.setItem(14, makeItem(Material.STAINED_CLAY, "§aG Note", 1, 5, "§7Click!"));
        inventory.setItem(15, makeItem(Material.STAINED_CLAY, "§eA Note", 1, 4, "§7Click!"));
        inventory.setItem(16, makeItem(Material.STAINED_CLAY, "§dB Note", 1, 6, "§7Click!"));
        inventory.setItem(22, makeItem(Material.STAINED_CLAY, "§3c Note", 1, 9, "§7Click!"));

        if(playingWithKeyboard.contains(player)) {
            ItemStack s = makeItem(Material.QUARTZ_STAIRS, "§bKeyboard mode", 1, 0, "§eClick to toggle!");
            ItemMeta smeta = s.getItemMeta();
            smeta.addEnchant(Enchantment.LUCK, 1, false);
            smeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            s.setItemMeta(smeta);

            inventory.setItem(4, s);
        } else {
            inventory.setItem(4, makeItem(Material.QUARTZ_STAIRS, "§bKeyboard mode", 1, 0, "§eClick to toggle!"));
        }

    }
}
