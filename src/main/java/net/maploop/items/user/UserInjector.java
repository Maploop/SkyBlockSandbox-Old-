package net.maploop.items.user;

import net.maploop.items.Items;
import net.maploop.items.services.ItemsService;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (user.getHealth() > user.getTotalHealth()) {
                    IUtil.sendActionText(player, "§6" + Math.round(user.getHealth()) + "/" + Math.round(user.getTotalHealth()) + "❤§a    " + Math.round(user.getDefense()) + "❈§a Defense§b    " + Math.round(user.getIntelligence()) + "/" + Math.round(user.getTotalIntelligence()) + "✎ Mana");
                } else {
                    IUtil.sendActionText(player, "§c" + Math.round(user.getHealth()) + "/" + Math.round(user.getTotalHealth()) + "❤§a    " + Math.round(user.getDefense()) + "❈§a Defense§b    " + Math.round(user.getIntelligence()) + "/" + Math.round(user.getTotalIntelligence()) + "✎ Mana");
                }

                if (user.getIntelligence() < user.getTotalIntelligence()) {
                    user.setIntelligence(user.getIntelligence() + user.getTotalIntelligence() / 10);
                }

                if (user.getHealth() < user.getTotalHealth()) {
                    user.setHealth(user.getHealth() + user.getTotalHealth() / 50);
                }

            }
        }, 0, 20L);
    }
}
