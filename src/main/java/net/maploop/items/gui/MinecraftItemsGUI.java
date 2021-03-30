package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.util.IUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MinecraftItemsGUI extends PaginatedGUI {
    public static Set<Player> mcSearching = new HashSet<>();
    public static Map<Player, String> mcSearch = new HashMap<>();

    public MinecraftItemsGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        int i = page + 1;
        return "Select an item (" + i + "/14" + ")";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public int getMaxItemsPerPage() {
        return 28;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        if(event.getCurrentItem().getType() == Material.AIR) return;
        if(event.getCurrentItem() == null) return;

        List<Material> items = new ArrayList<>(Arrays.asList(Material.values()));
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case COMMAND: {
                event.setCancelled(true);
                player.sendMessage("§cThis item is unobtainable!");
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
            }
            case COMMAND_MINECART: {
                event.setCancelled(true);
                player.sendMessage("§cThis item is unobtainable!");
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
            }
            case BARRIER: {
                if(event.getCurrentItem().hasItemMeta()) {
                    player.closeInventory();
                } else {
                    player.sendMessage("§cThis item is unobtainable!");
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                }
                break;
            }
            case STAINED_GLASS_PANE: {
                if(event.getCurrentItem().hasItemMeta()) {
                    event.setCancelled(true);
                } else {
                    player.getInventory().addItem(event.getCurrentItem());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    break;
                }
                break;
            }
            case SIGN: {
                if(event.getCurrentItem().hasItemMeta()) {
                    player.sendMessage("§aPlease enter your search in chat.");
                    mcSearching.add(player);
                    player.closeInventory();
                } else {
                    player.getInventory().addItem(event.getCurrentItem());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    break;
                }
                break;
            }
            case ANVIL: {
                if(event.getCurrentItem().hasItemMeta()) {
                    mcSearch.remove(player);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new MinecraftItemsGUI(new PlayerMenuUtility(player)).open();
                        }
                    }.runTaskLater(Items.getInstance(), 3);
                } else {
                        player.getInventory().addItem(event.getCurrentItem());
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                        break;
                }

                break;
            }
            case HOPPER: {
                if(event.getCurrentItem().hasItemMeta()) {
                    event.setCancelled(true);
                    player.getInventory().clear();
                }
            }
            case ARROW: {
                if(!event.getCurrentItem().hasItemMeta()) {
                    player.getInventory().addItem(event.getCurrentItem());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                    break;
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§aNext")) {
                    if(!((index + 1) >= items.size())) {
                        page = page + 1;
                        super.open();
                    } else {
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                        player.sendMessage(ChatColor.RED + "You are on the last page.");
                    }
                    break;
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§aPrevious")) {
                    if (page == 0){
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                        player.sendMessage(ChatColor.RED + "You are already on the first page.");
                    }else{
                        page = page - 1;
                        super.open();
                    }
                    break;
                }
                break;
            }
            default: {
                if(event.getSlot() < 45) {
                    player.getInventory().addItem(event.getCurrentItem());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                }
                break;
            }
        }
    }

    @Override
    public void setItems() {
        fillBorder();
        List<Material> items = new ArrayList<>(Arrays.asList(Material.values()));
        items.remove(Material.AIR);
        items.removeAll(Collections.singletonList(Material.AIR));

        ItemStack next = makeItem(Material.ARROW, ChatColor.GREEN + "Next Page", 1, 0, "§7Go to the next page.");
        ItemStack prev = makeItem(Material.ARROW, ChatColor.GREEN + "Previous Page", 1, 0, "§7Go to the previous page.");
        inventory.setItem(53, next);

        if(page > 0) {
            inventory.setItem(45, prev);
        }

        ItemStack searchItem = makeItem(Material.SIGN, "§aSearch", 1, 0,
                "§7Click to search for",
                "§7an Item in this menu!",
                "",
                "§eClick to search!");
        inventory.setItem(50, searchItem);

        inventory.setItem(4, makeItem(Material.HOPPER, "§aClear Inventory", 1, 0,
                IUtil.colorize("&7Clear your inventory off all\n&7annoying and useless items!\n\n&eClick to clear!")));

        ItemStack resetSearch = makeItem(Material.ANVIL, "§eReset Search", 1, 0,
                "§7You are currently searching",
                "§7for an item, click to reset your search",
                "",
                "§7Current Search: §a" + mcSearch.get(playerMenuUtility.getOwner()),
                "",
                "§eClick to reset!");

        ItemStack close = makeItem(Material.BARRIER, "§cClose", 1, 0);
        inventory.setItem(49, close);

        if (mcSearch.containsKey(playerMenuUtility.getOwner())) {
            inventory.setItem(48, resetSearch);

            List<ItemStack> matches = searchFor(mcSearch.get(playerMenuUtility.getOwner()), inventory, playerMenuUtility.getOwner());

            if(!matches.isEmpty()) {
                for(int i = 0; i < getMaxItemsPerPage(); i++) {
                    index = getMaxItemsPerPage() * page + i;
                    if(index >= matches.size()) break;
                    inventory.addItem(matches.get(index));
                }
            }
            return;
        }

        if(!items.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= items.size()) break;
                inventory.addItem(new ItemStack(items.get(index), 1));
            }
        }
    }

    private List<ItemStack> searchFor(String whatToSearch, Inventory inv, Player player) {
        List<ItemStack> matches = new ArrayList<>();
        List<ItemStack> toBeChecked = new ArrayList<>();
        List<Material> list = new ArrayList<>(Arrays.asList(Material.values()));
        list.remove(Material.AIR);
        list.removeAll(Collections.singletonList(Material.AIR));

        for(Material mat : list) {
            ItemStack item = new ItemStack(mat, 1);

            toBeChecked.add(item);
        }
        for(ItemStack item : toBeChecked) {
            if(ChatColor.stripColor(item.getType().name().replace("_", " ").toLowerCase()).contains(whatToSearch.toLowerCase())) {
                matches.add(item);
            }
        }

        return matches;
    }
}
