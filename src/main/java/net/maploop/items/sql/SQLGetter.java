package net.maploop.items.sql;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import net.maploop.items.Items;
import org.bukkit.entity.Player;

public class SQLGetter
{
    private final Player player;
    private final Items plugin;

    public SQLGetter(final Player player, final Items plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    public void inject() {
        this.createPlayer();
    }

    public static void createTable() {
        final Items plugin = Items.getInstance();
        try {
            final PreparedStatement statement = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Player_Data` (`UUID` varchar(50),`Wardobe` LONGTEXT NOT NULL)");
            statement.executeUpdate();
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public void createPlayer() {
        try {
            if (!this.exists()) {
                final PreparedStatement preparedStatement1 = this.plugin.sql.getConnection().prepareStatement("INSERT INTO `Player_Data` (`UUID`,`Wardobe`) VALUES (?, ?)");
                preparedStatement1.setString(1, this.player.getUniqueId().toString());
                preparedStatement1.setString(2, "NONE");
                preparedStatement1.executeUpdate();
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean exists() {
        try (final PreparedStatement ps = this.plugin.sql.getConnection().prepareStatement("SELECT * FROM `Player_Data` WHERE `UUID`=?;")) {
            ps.setString(1, this.player.getUniqueId().toString());
            try (final ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void setWardobe(final String d) {
        try {
            final PreparedStatement statement = this.plugin.sql.getConnection().prepareStatement("UPDATE `Player_Data` SET `Wardobe`=? WHERE `UUID`=?");
            statement.setString(1, d);
            statement.setString(2, this.player.getUniqueId().toString());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getWardobe() {
        try (final PreparedStatement ps = this.plugin.sql.getConnection().prepareStatement("SELECT `Wardobe` FROM `Player_Data` WHERE `UUID`=?")) {
            ps.setString(1, this.player.getUniqueId().toString());
            try (final ResultSet rs = ps.executeQuery()) {
                String coins = null;
                if (rs.next()) {
                    coins = rs.getString("Wardobe");
                    return coins;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
