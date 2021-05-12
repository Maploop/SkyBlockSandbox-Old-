package net.maploop.items.user;

import net.maploop.items.Items;
import net.maploop.items.services.ItemsService;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
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
        user.setHealth(user.getTotalHealth());
        user.setIntelligence(user.getTotalIntelligence());
    }
}
