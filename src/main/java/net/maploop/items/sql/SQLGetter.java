package net.maploop.items.sql;

import net.maploop.items.Items;
import net.maploop.items.util.DUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class SQLGetter {
    private final Player player;
    private final Items plugin;

    public SQLGetter(Player player, Items plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    public void inject() {
        createPlayer();
        player.sendMessage("§eWelcome §a" + player.getName() + "§e to Dungeons Simulator!");
        player.sendMessage("§7You are playing on profile:");
        TextComponent uuid = DUtil.makeClickableText(ChatColor.DARK_GRAY + getUniqueId(), "§eClick to type the UUID in your chatbox!", ClickEvent.Action.SUGGEST_COMMAND, getUniqueId());
        player.spigot().sendMessage(uuid);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1.5f);
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(Items.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(plugin.getConfig().getBoolean("use-actionbar")) {
                    if (getHealth() > getMaxHealth()) {
                        DUtil.sendActionText(player, "§6" + Math.round(getHealth()) + "/" + Math.round(getMaxHealth()) + "❤§a    " + Math.round(getDefense()) + "❈§b    " + Math.round(getMana()) + "/" + Math.round(getMaxMana()) + "✎");
                    } else {
                        DUtil.sendActionText(player, "§c" + Math.round(getHealth()) + "/" + Math.round(getMaxHealth()) + "❤§a    " + Math.round(getDefense()) + "❈§b    " + Math.round(getMana()) + "/" + Math.round(getMaxMana()) + "✎");
                    }
                }

                if(getMana() < getMaxMana()) {
                    setMana(getMana() + getMaxMana() / 10);
                }

                if(getHealth() < getMaxHealth()) {
                    setHealth(getHealth() + getMaxHealth() / 50);
                }

            }
        }, 0, 20L);
    }

    public static void createTable() {
        Items plugin = Items.getInstance();
        try {
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Skyblock_Data` (`UUID` varchar(50),`Dungeon_Class` VARCHAR (50),`Catacombs_Level` INT(60),`Mage_Class_Level` INT(50), `Berserk_Class_Level` INT(50), `Healer_Class_Level` INT(50), `Archer_Class_Level` INT(50), `Tank_Class_Level` INT(50), " +
                    "`EXP` DOUBLE(16,10) , `Required_XP` DOUBLE(16,1), `Profile_UUID` VARCHAR(50), `Defense` DOUBLE(16,10), `Coins` " +
                    "DOUBLE(30,10) unsigned, `Max_Health` DOUBLE(16,10), `Health` DOUBLE(16,10), `Max_Mana` DOUBLE(16,10), `Mana` DOUBLE(16,10), `Crit_Damage` DOUBLE(16,10), `Crit_Chance` DOUBLE(16,10), `Profile_Viewer` VARCHAR(5), `Strength` DOUBLE(16,10))");
            statement.executeUpdate();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public void createPlayer() {
        try {
            if (!(exists())) {
                // 20
                PreparedStatement preparedStatement1 = plugin.sql.getConnection().prepareStatement("INSERT INTO `Skyblock_Data` (`UUID`,`Dungeon_Class`,`Catacombs_Level` ,`Mage_Class_Level`, `Berserk_Class_Level`, `Healer_Class_Level`, `Archer_Class_Level`, `Tank_Class_Level`, `EXP`, `Required_XP`, `Profile_UUID`," +
                        "`Defense`, `Max_Health`, `Health`, `Max_Mana`, `Mana`, `Crit_Damage`, `Crit_Chance`, `Profile_Viewer`, `Strength`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement1.setString(1, player.getUniqueId().toString());
                preparedStatement1.setString(2, "NONE");
                preparedStatement1.setInt(3, 0);
                preparedStatement1.setInt(4, 0);
                preparedStatement1.setInt(5, 0);
                preparedStatement1.setInt(6, 0);
                preparedStatement1.setInt(7, 0);
                preparedStatement1.setInt(8, 0);
                preparedStatement1.setDouble(9, 0);
                preparedStatement1.setDouble(10, 0);
                preparedStatement1.setString(11, UUID.randomUUID().toString());
                preparedStatement1.setDouble(12, 0.0);
                preparedStatement1.setDouble(13, 0.0);
                preparedStatement1.setDouble(14, 0.0);
                preparedStatement1.setDouble(15, 0.0);
                preparedStatement1.setDouble(16, 0.0);
                preparedStatement1.setDouble(17, 0.0);
                preparedStatement1.setDouble(18, 0.0);
                preparedStatement1.setBoolean(19, true);
                preparedStatement1.setDouble(20, 0.0);

                preparedStatement1.executeUpdate();

                setMaxHealth(100.0);
                setMaxMana(100.0);
                setMana(100.0);
                setHealth(100.0);
                setDefense(50.0);
                setCoins(100.2);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean exists() {
        try (PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM `Skyblock_Data` WHERE `UUID`=?;")) {
            ps.setString(1, player.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Errored
        }
        return false;
    }

    public void setCoins(double d) {
        try {
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Coins`=? WHERE `UUID`=?");
            statement.setDouble(1, d);
            statement.setString(2, player.getUniqueId().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getCoins() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Coins` FROM `Skyblock_Data` WHERE `UUID`=?")){
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double coins = 0;
                if(rs.next()){
                    coins = rs.getDouble("Coins");
                    return coins;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setDefense(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Defense`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getDefense() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Defense` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double defense = 0;
                if(rs.next()) {
                    defense = rs.getDouble("Defense");
                    return defense;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void setHealth(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Health`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getHealth() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Health` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double health = 0;
                if(rs.next()){
                    health = rs.getDouble("Health");
                    return health;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String getUniqueId() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Profile_UUID` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                String uuid = "";
                if(rs.next()) {
                    uuid = rs.getString("Profile_UUID");
                    return uuid;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMaxHealth(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Max_Health`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getMaxHealth() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Max_Health` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double maxHealth = 0;
                if(rs.next()) {
                    maxHealth = rs.getDouble("Max_Health");
                    return maxHealth;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setMana(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Mana`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getMana() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Mana` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double maxHealth = 0;
                if(rs.next()) {
                    maxHealth = rs.getDouble("Mana");
                    return maxHealth;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setMaxMana(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Max_Mana`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getMaxMana() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Max_Mana` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double maxHealth = 0;
                if(rs.next()) {
                    maxHealth = rs.getDouble("Max_Mana");
                    return maxHealth;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setStrength(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Strength`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getStrength() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Strength` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double maxHealth = 0;
                if(rs.next()) {
                    maxHealth = rs.getDouble("Strength");
                    return maxHealth;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCritDamage(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Skyblock_Data` SET `Crit_Damage`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getCritDamage() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Crit_Damage` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double maxHealth = 0;
                if(rs.next()) {
                    maxHealth = rs.getDouble("Crit_Damage");
                    return maxHealth;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCritChance(double d) {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE `Crit_Chance` SET `Crit_Damage`=? WHERE `UUID`=?")) {
            ps.setDouble(1, d);
            ps.setString(2, player.getUniqueId().toString());

            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getCritChance() {
        try(PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT `Crit_Chance` FROM `Skyblock_Data` WHERE `UUID`=?")) {
            ps.setString(1, player.getUniqueId().toString());
            try(ResultSet rs = ps.executeQuery()) {
                double maxHealth = 0;
                if(rs.next()) {
                    maxHealth = rs.getDouble("Crit_Chance");
                    return maxHealth;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

