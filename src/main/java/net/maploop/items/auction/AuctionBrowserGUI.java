package net.maploop.items.auction;

import net.maploop.items.gui.PaginatedGUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemUtilities;
import net.maploop.items.mongo.MongoConnect;
import net.maploop.items.util.BukkitSerialization;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionBrowserGUI extends PaginatedGUI {
    public static boolean DISABLED = false;

    private final static MongoConnect DB = new MongoConnect();
    public final static List<AuctionItem> ITEMS = new ArrayList<>();

    private final AuctionCategory category;

    public AuctionBrowserGUI(PlayerMenuUtility playerMenuUtility, AuctionCategory category) {
        super(playerMenuUtility);
        this.category = category;
    }

    @Override
    public int getMaxItemsPerPage() {
        return 24;
    }

    @Override
    public String getTitle() {
        return "Auction Browser";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;

        if (new ItemUtilities.AuctionData().isAuctionedItem(event.getCurrentItem())) {
            String id = new ItemUtilities.AuctionData().getString(event.getCurrentItem(), "id");

            AuctionItem i = new AuctionItemHandler(UUID.fromString(id),
                    Integer.parseInt(DB.getData("auctions", id, "price").toString()),
                    Bukkit.getOfflinePlayer(UUID.fromString(DB.getData("auctions", id, "owner").toString())),
                    BukkitSerialization.itemStackFromBase64(DB.getData("auctions", id, "item-stack").toString()),
                    Boolean.parseBoolean(DB.getData("auctions", id, "bin").toString()),
                    Long.parseLong(DB.getData("auctions", id, "end-time").toString())
                    );

            new AuctionInspectGUI(playerMenuUtility, i).open();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            case DIAMOND_SWORD:
                if (event.getCurrentItem().getItemMeta().getLore().contains("§8Category")) {
                    new AuctionBrowserGUI(playerMenuUtility, AuctionCategory.WEAPONS).open();
                    return;
                }
                break;
            case DIAMOND_CHESTPLATE:
                if (event.getCurrentItem().getItemMeta().getLore().contains("§8Category")) {
                    new AuctionBrowserGUI(playerMenuUtility, AuctionCategory.ARMOR).open();
                    return;
                }
                break;
            case SKULL_ITEM:
                if (event.getCurrentItem().getItemMeta().getLore().contains("§8Category")) {
                    new AuctionBrowserGUI(playerMenuUtility, AuctionCategory.ACCESSORIES).open();
                    return;
                }
                break;
            case APPLE:
                if (event.getCurrentItem().getItemMeta().getLore().contains("§8Category")) {
                    new AuctionBrowserGUI(playerMenuUtility, AuctionCategory.CONSUMABLES).open();
                    return;
                }
                break;
            case COBBLESTONE:
                if (event.getCurrentItem().getItemMeta().getLore().contains("§8Category")) {
                    new AuctionBrowserGUI(playerMenuUtility, AuctionCategory.BLOCKS).open();
                    return;
                }
                break;
            case STICK:
                if (event.getCurrentItem().getItemMeta().getLore().contains("§8Category")) {
                    new AuctionBrowserGUI(playerMenuUtility, AuctionCategory.TOOLS_MISC).open();
                    return;
                }
                break;
            case ARROW: {
                if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§aNext")) {
                    if(!((index + 1) >= ITEMS.size())) {
                        page = page + 1;
                        super.open();
                    } else {
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                        player.sendMessage(ChatColor.RED + "You are on the last page.");
                    }
                    return;
                } else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§aPrevious")) {
                    if (page == 0){
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0f);
                        player.sendMessage(ChatColor.RED + "You are already on the first page.");
                    }else{
                        page = page - 1;
                        super.open();
                    }
                    return;
                }
                break;
            }
            case STAINED_GLASS_PANE: return;
        }
    }

    @Override
    public void setItems() {
        if (DISABLED) {
            playerMenuUtility.getOwner().closeInventory();
            playerMenuUtility.getOwner().sendMessage("§cThe auction house is temporarily disabled!");
            return;
        }

        final ItemStack A = makeItem(Material.STAINED_GLASS_PANE, " ", 1, this.category.getIndex());

        inventory.setItem(1, A);
        inventory.setItem(2, A);
        inventory.setItem(3, A);
        inventory.setItem(4, A);
        inventory.setItem(5, A);
        inventory.setItem(6, A);
        inventory.setItem(7, A);
        inventory.setItem(8, A);
        inventory.setItem(10, A);
        inventory.setItem(17, A);
        inventory.setItem(19, A);
        inventory.setItem(26, A);
        inventory.setItem(28, A);
        inventory.setItem(35, A);
        inventory.setItem(37, A);
        inventory.setItem(44, A);
        inventory.setItem(46, A);
        inventory.setItem(47, A);
        inventory.setItem(48, A);
        inventory.setItem(49, A);
        inventory.setItem(50, A);
        inventory.setItem(51, A);
        inventory.setItem(52, A);
        inventory.setItem(53, A);

        inventory.setItem(0, makeItem(Material.DIAMOND_SWORD, "§6Weapons", 1, 0, IUtil.colorize("&8Category")));
        inventory.setItem(9, makeItem(Material.DIAMOND_CHESTPLATE, "§9Armor", 1, 0, IUtil.colorize("&8Category")));
        inventory.setItem(18, makeCustomSkullItem("http://textures.minecraft.net/texture/4fcee1d580881a4384828f1b94a0decacec84384c4da64473710466246a4ece6", "§2Accessories", 1, IUtil.colorize("&8Category")));
        inventory.setItem(27, makeItem(Material.APPLE, "§cConsumables", 1, 0, "§8Category"));
        inventory.setItem(36, makeItem(Material.COBBLESTONE, "§eBlocks", 1, 0, "§8Category"));
        inventory.setItem(45, makeItem(Material.STICK, "§dTools & Misc", 1, 0, "§8Category"));

        if (this.page > 0) {
            inventory.setItem(46, makeItem(Material.ARROW, "§aPrevious Page", 1, 0, "§eClick to view!"));
        }
        inventory.setItem(53, makeItem(Material.ARROW, "§aNext Page", 1, 0, "§eClick to view!"));

        if(ITEMS != null && !ITEMS.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= ITEMS.size()) break;
                AuctionItem item = ITEMS.get(index);
                if (item.getCategory() != this.category) continue;

                ItemStack s = item.getStack();
                ItemStack s1 = new ItemUtilities.AuctionData().put(s, "id", item.getId().toString());

                ItemMeta meta = s1.getItemMeta();
                List<String> lore = new ArrayList<>(s1.getItemMeta().getLore());
                lore.add("§8§m---------------");
                lore.add("§7Seller: " + item.getOwner().getPlayer().getDisplayName());
                lore.add(item.isBIN() ? "§7Buy it now: §6" + item.getPrice() : "§7Starting bid: §6" + new DecimalFormat("#,###").format(item.getPrice()));
                lore.add("");
                lore.add(item.isBIN() ? "" : "§7Highest bid: §6" + new DecimalFormat("#,###").format(Integer.parseInt(DB.getData("auctions", item.getId().toString(), "top-bid-amount").toString())));
                lore.add("§7Bidder: " + (item.getHighestBidder() != null ? item.getHighestBidder().getPlayer().getDisplayName() : "§8None"));
                lore.add("");
                lore.add("§eClick to inspect!");
                meta.setLore(lore);
                s1.setItemMeta(meta);

                inventory.addItem(s1);
            }
        }
    }

    public static void put(AuctionItem item) {

        ITEMS.add(item);
    }

    public enum AuctionCategory {
        WEAPONS("Weapons", 1),
        ARMOR("Armor", 11),
        ACCESSORIES("Accessories", 13),
        CONSUMABLES("Comsumables ", 14),
        BLOCKS("Blocks", 12),
        TOOLS_MISC("Tools & Misc", 10),
        A("A", 0);

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        private final int index;
        private final String name;

        AuctionCategory(String name, int i) {
            this.name = name;
            this.index = i;
        }
    }
}
