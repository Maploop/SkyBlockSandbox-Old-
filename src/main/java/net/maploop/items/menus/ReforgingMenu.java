package net.maploop.items.menus;

import net.maploop.items.Items;
import net.maploop.items.helpers.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReforgingMenu extends Menu {
    public static Map<UUID, Runnable> reforging = new HashMap<>();
    private ItemStack errorSides = makeItem(Material.STAINED_GLASS_PANE, "§cError!", 1, 14, "§7Cannot reforge item!");
    private ItemStack successSides = makeItem(Material.STAINED_GLASS_PANE, "§aSuccess!", 1, 5, "§7Able to reforge item!");

    private ItemStack errorReforge = makeItem(Material.BARRIER, "§cCannot reforge items!", 1, 0, "§7These items can't be reforged!");
    private ItemStack successReforge = makeItem(Material.ANVIL, "§aReforge", 1, 0, "§7Click to reforge item!");

    public ReforgingMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Reforge an Item";
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack item = inv.getItem(13);
        startChecks(inv);

        switch (event.getCurrentItem().getType()) {
            case BARRIER:
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("§cClose")) {
                    event.setCancelled(true);
                    player.closeInventory();
                    break;
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("§cCannot")) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Cannot reforge this item!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0);
                    break;
                }
                break;
            case STAINED_GLASS_PANE:
                event.setCancelled(true);
                break;
            case ANVIL:
                event.setCancelled(true);
                player.sendMessage(ChatColor.GREEN + "You reforged your " + inv.getItem(13).getItemMeta().getDisplayName() + ChatColor.GREEN + "!");
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);

                int reforgeId = Utilities.getRandomInteger(2);
                if (reforgeId == 0) {
                      ItemStack reforged = item;
                      ItemMeta meta = item.getItemMeta();
                      meta.setDisplayName(ChatColor.getLastColors(meta.getDisplayName()) + "Sharp " + meta.getDisplayName());
                      reforged.setItemMeta(meta);
                      inv.setItem(13, reforged);
                }
                if (reforgeId == 1) {
                    ItemStack reforged = item;
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.getLastColors(meta.getDisplayName()) + "Vivid " + meta.getDisplayName());
                    reforged.setItemMeta(meta);
                    inv.setItem(13, reforged);
                }
                if (reforgeId == 2) {
                    ItemStack reforged = item;
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.getLastColors(meta.getDisplayName()) + "Spicy " + meta.getDisplayName());
                    reforged.setItemMeta(meta);
                    inv.setItem(13, reforged);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void setItems() {
        setFilter();
        ItemStack close = makeItem(Material.BARRIER, "§cClose", 1, 0);
        inventory.setItem(40, close);
        inventory.setItem(13, null);
        inventory.setItem(22, errorReforge);
        fillSides(errorSides);

    }

    private void startChecks(Inventory inventory) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(inventory.getItem(13) == null)) {
                    if (inventory.getItem(13).hasItemMeta() && inventory.getItem(13).getItemMeta().hasDisplayName()) {
                        if (ChatColor.stripColor(inventory.getItem(13).getType().toString().toLowerCase()).contains("sword") || inventory.getItem(13).getItemMeta().getLore().contains("§8This item can be reforged!")) {
                            fillSides(successSides);
                            inventory.setItem(22, successReforge);
                        }
                    } else {
                        inventory.setItem(22, errorReforge);
                    }
                } else {
                    fillSides(errorSides);
                    inventory.setItem(22, errorReforge);
                }
            }
        }.runTaskTimer(Items.getInstance(), 0, 1);
    }
}
