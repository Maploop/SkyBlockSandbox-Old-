package net.maploop.items.user;

import net.maploop.items.Items;
import net.maploop.items.services.ItemsService;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UserInjector extends ItemsService {
    private final User user;

    public UserInjector(User user) {
        super(user);
        this.user = user;
    }

    @Override
    public String getServiceName() {
        return "User Injector";
    }

    public void inject() {
        Player player = user.getBukkitPlayer();
        user.setHealth(user.getTotalHealth());
        user.setIntelligence(user.getTotalIntelligence());
        updateStats();
        updateActionBar();

    }
    private void updateStats () {
        Player player = user.getBukkitPlayer();
        if (player.isOnline()) {
            int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
                @Override
                public void run() {
                    try {
                        if (user.getHealth() < user.getTotalHealth()) {
                            user.setHealth(user.getHealth() + (user.getTotalHealth() * 0.04));
                        }
                        if (user.getHealth() > user.getTotalHealth()) {
                            user.setHealth(user.getTotalHealth());
                        }
                    } catch (NullPointerException e) {
                    }
                }
            }, 1, 80);
            int taskid2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
                @Override
                public void run() {
                    try {
                        if (user.getIntelligence() < user.getTotalIntelligence()) {
                            user.setIntelligence(user.getIntelligence() + (user.getTotalIntelligence() * 0.04));
                        }
                        if (user.getIntelligence() > user.getTotalIntelligence()) {
                            user.setIntelligence(user.getTotalIntelligence());
                        }
                    } catch (NullPointerException e) {
                    }
                }
            }, 1, 20);
        }
    }

    private void updateActionBar () {
        Player player = user.getBukkitPlayer();
        int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    try {
                        if(IUtil.abilityUsed.containsKey(player)){
                            if(IUtil.abilityUsed.get(player)){
                                IUtil.scheduleTask(() -> {
                                    IUtil.abilityUsed.put(player,false);
                                },20);
                                return;
                            }
                        }
                        if (user.getTotalDefense() == 0) {
                            IUtil.sendActionText(player, "§c" + Math.round(user.getHealth()) + "/" + Math.round(user.getTotalHealth()) + "❤§b    " + Math.round(user.getIntelligence()) + "/" + Math.round(user.getTotalIntelligence()) + "✎ Mana");
                        } else {
                            IUtil.sendActionText(player, "§c" + Math.round(user.getHealth()) + "/" + Math.round(user.getTotalHealth()) + "❤§a    " + Math.round(user.getTotalDefense()) + "❈§a Defense§b    " + Math.round(user.getIntelligence()) + "/" + Math.round(user.getTotalIntelligence()) + "✎ Mana");
                        }
                        if (user.getIntelligence() > user.getTotalIntelligence()) {
                            user.setIntelligence(user.getTotalIntelligence());
                        }
                        if (user.getHealth() > user.getTotalHealth()) {
                            user.setHealth(user.getTotalHealth());
                        }
                    } catch (NullPointerException e) {
                    }
                }
            }
        }, 1, 5);
    }
}
