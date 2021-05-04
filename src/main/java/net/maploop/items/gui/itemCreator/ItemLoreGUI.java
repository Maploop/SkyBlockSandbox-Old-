package net.maploop.items.gui.itemCreator;

import net.maploop.items.Items;
import net.maploop.items.gui.AnvilGUI;
import net.maploop.items.gui.GUI;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.util.IUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemLoreGUI extends GUI {
    public ItemLoreGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Edit item lore";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 31: {
                new ItemCreatorGUI(new PlayerMenuUtility(player)).open();
                break;
            }
            case 35: {
                if(player.getItemInHand().hasItemMeta()) {
                    if(player.getItemInHand().getItemMeta().hasLore()) {
                        if(player.getItemInHand().getItemMeta().getLore().size() > 24) {
                            player.sendMessage("§e------------------------------------------------");
                            player.sendMessage("§cYou have reached the item lore limit on your item!");
                            player.sendMessage("§cIf you want to increase it, you can buy a rank\nat §6§nhttps://store.skyblocksandbox.net§c!");
                            player.sendMessage("§e------------------------------------------------");
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 0);
                            return;
                        }
                    }
                }

                AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                    @Override
                    public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                        if(event.getSlot().equals(AnvilGUI.AnvilSlot.INPUT_LEFT)) {
                            event.setWillClose(false);
                            event.setWillDestroy(false);
                        }
                        if(event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                            String n = IUtil.colorize(event.getName());
                            if(player.getItemInHand().getItemMeta().hasLore()) {
                                ItemMeta meta = player.getItemInHand().getItemMeta();
                                List<String> l = new ArrayList<>(meta.getLore());
                                l.add(n);
                                meta.setLore(l);
                                player.getItemInHand().setItemMeta(meta);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        setItems();
                                        new ItemLoreGUI(new PlayerMenuUtility(player)).open();
                                    }
                                }.runTaskLater(Items.getInstance(), 2);

                                return;
                            }
                            ItemMeta meta = player.getItemInHand().getItemMeta();
                            List<String> l = new ArrayList<>();
                            l.add(n);
                            meta.setLore(l);
                            player.getItemInHand().setItemMeta(meta);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    setItems();
                                    new ItemLoreGUI(new PlayerMenuUtility(player)).open();
                                }
                            }.runTaskLater(Items.getInstance(), 2);
                        }
                    }
                });

                gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Lore input", 1, 0));
                gui.open();
                break;
            }
            default: {
                if(event.getCurrentItem().getType().equals(Material.PAPER)) {
                    if(event.getClick().equals(ClickType.RIGHT)) {
                        String[] s = event.getCurrentItem().getItemMeta().getDisplayName().split("#");
                        int i = Integer.parseInt(s[1]) -1;
                        ItemMeta meta = player.getItemInHand().getItemMeta();
                        List<String> lore = player.getItemInHand().getItemMeta().getLore();
                        lore.remove(lore.get(i));
                        meta.setLore(lore);
                        player.getItemInHand().setItemMeta(meta);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                setItems();
                                new ItemLoreGUI(playerMenuUtility).open();
                            }
                        }.runTaskLater(Items.getInstance(), 2);
                        return;
                    }
                    String[] s = event.getCurrentItem().getItemMeta().getDisplayName().split("#");
                    int i = Integer.parseInt(s[1]) -1;

                    AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                        @Override
                        public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                            if(event.getSlot().equals(AnvilGUI.AnvilSlot.INPUT_LEFT)) {
                                event.setWillClose(false);
                                event.setWillDestroy(false);
                            }
                            if(event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                                String n = IUtil.colorize(event.getName());
                                ItemMeta meta = player.getItemInHand().getItemMeta();
                                List<String> l = new ArrayList<>(meta.getLore());
                                l.set(i, n);
                                meta.setLore(l);
                                player.getItemInHand().setItemMeta(meta);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        setItems();
                                        new ItemLoreGUI(new PlayerMenuUtility(player)).open();
                                    }
                                }.runTaskLater(Items.getInstance(), 2);
                            }
                        }
                    });
                    gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, makeItem(Material.PAPER, "Lore input", 1, 0));
                    gui.open();
                }
            }
        }
    }

    @Override
    public void setItems() {
        Player player = playerMenuUtility.getOwner();

        fillBottom();
        inventory.setItem(31, makeItem(Material.ARROW, "§aGo Back", 1, 0, "§7To Create an item"));

        inventory.setItem(35, makeItem(Material.WOOL, "§aAdd line", 1, 5, IUtil.colorize("&7Add a line of lore to\n&7your item!\n\n&eClick to add!")));

        if(player.getItemInHand().getItemMeta().hasLore()){
            for(int i = 0; i < player.getItemInHand().getItemMeta().getLore().size(); ++i){
                int w = i +1;
                inventory.addItem(makeItem(Material.PAPER, "§aLore #" + w, 1, 0, IUtil.colorize("§7Lore Content:\n" + player.getItemInHand().getItemMeta().getLore().get(i) + "\n\n&eClick to edit!\n&eRight-Click to remove")));
            }
        }
    }
}
