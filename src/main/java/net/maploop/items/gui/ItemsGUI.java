package net.maploop.items.gui;

import net.maploop.items.Items;
import net.maploop.items.item.CustomItem;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.item.SBItems;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ItemsGUI extends PaginatedGUI {
    public static Set<Player> searching = new HashSet<>();
    public static Map<Player, String> search = new HashMap<>();

    public ItemsGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        int i = page + 1;
        int i1 = index + 1;
        return "Select an Item (" + i + ")";
    }

    @Override
    public int getMaxItemsPerPage() {
        return 28;
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        if(event.getCurrentItem().getType() == Material.AIR) return;
        if(event.getCurrentItem() == null) return;

        List<ItemStack> items = SBItems.getItems();
        Player player = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case BARRIER: {
                player.closeInventory();
                break;
            }
            case STAINED_GLASS_PANE: {
                event.setCancelled(true);
                break;
            }
            case SIGN: {
                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                            event.setWillClose(true);
                            event.setWillDestroy(true);

                            search.put(player, event.getName());
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    new ItemsGUI(new PlayerMenuUtility(player)).open();
                                }
                            }.runTaskLater(Items.getInstance(), 3);
                        }else {
                            event.setWillClose(false);
                            event.setWillDestroy(false);
                        }
                    }
                });

                ItemStack i = makeItem(Material.PAPER, "Enter your search", 1, 0);
                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, i);
                gui.open();
                break;
            }
            case ANVIL: {
                search.remove(player);
                player.playSound(player.getLocation(), Sound.CAT_MEOW, 1f, 1.5f);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new ItemsGUI(new PlayerMenuUtility(player)).open();
                    }
                }.runTaskLater(Items.getInstance(), 3);

                break;
            }
            case HOPPER: {
                player.getInventory().clear();
                player.sendMessage("§aWhoop!");

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getInventory().setItem(8, CustomItem.fromString(Items.getInstance(), "skyblock_menu", 1));
                    }
                }.runTaskLater(Items.getInstance(), 3);
                break;
            }
            case ARROW: {
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
                net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(event.getCurrentItem());
                NBTTagCompound tag = (stack.hasTag()) ? stack.getTag() : new NBTTagCompound();
                if(!(tag.hasKey("is-SB"))) return;
                if(!(tag.getString("is-SB").equals("true"))) return;

                player.getInventory().addItem(CustomItem.fromString(Items.getInstance(), tag.getString("SB-name"), 1));
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
            }
        }
    }

    @Override
    public void setItems() {
        fillBorder();

        List<ItemStack> items = new ArrayList<>(SBItems.getItems());


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

        ItemStack clearInv = makeItem(Material.HOPPER, "§aClear Inventory", 1, 0, "§7Click you clear your\n§7inventory off of all the\n§7junk in there!\n \n§eClick to clear!");
        inventory.setItem(4, clearInv);

        ItemStack resetSearch = makeItem(Material.ANVIL, "§eReset Configurations", 1, 0,
                "§7You are currently searching",
                "§7for an item, click to reset your search",
                "",
                "§7Current Search: §a" + search.get(playerMenuUtility.getOwner()),
                "",
                "§eClick to reset!");

        ItemStack close = makeItem(Material.BARRIER, "§cClose", 1, 0);
        inventory.setItem(49, close);

        if (search.containsKey(playerMenuUtility.getOwner())) {
            inventory.setItem(48, resetSearch);

            List<ItemStack> matches = searchFor(search.get(playerMenuUtility.getOwner()), inventory, playerMenuUtility.getOwner());

            if(items != null && !matches.isEmpty()) {
                for(int i = 0; i < getMaxItemsPerPage(); i++) {
                    index = getMaxItemsPerPage() * page + i;
                    if(index >= matches.size()) break;
                    inventory.addItem(matches.get(index));
                }
            }
            return;
        }

        if(items != null && !items.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= items.size()) break;
                inventory.addItem(items.get(index));
            }
        }
    }


    private List<ItemStack> searchFor(String whatToSearch, Inventory inv, Player player) {
        List<ItemStack> matches = new ArrayList<>();
        List<ItemStack> toBeChecked = new ArrayList<>();
        List<ItemStack> list = SBItems.getItems();

        for(ItemStack item : list) {
            if(item != null) {
                if(ItemUtilities.isCustomItem(item)) {
                    toBeChecked.add(item);
                }
            }
        }
        for(ItemStack item : toBeChecked) {
            if(ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase()).contains(whatToSearch.toLowerCase())) {
                matches.add(item);
            }
        }

        return matches;
    }
}
