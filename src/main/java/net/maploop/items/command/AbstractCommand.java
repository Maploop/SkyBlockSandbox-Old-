package net.maploop.items.command;

import net.maploop.items.Items;
import net.maploop.items.user.User;
import net.maploop.items.util.IUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
    public static final String COMMAND_PREFIX = "Command_";

    private static CommandMap cmap;
    private final String name;
    private final String description;
    private final String usage;
    private final String alaises;
    private final CommandParameters params;
    private final CommandSource source;

    private CommandUser sender;
    private String[] args;

    protected final Items plugin = Items.getInstance();
    protected final Server server = plugin.getServer();

    public static final String NO_PERMISSION = "§cYou must be admin or higher to use this command!";
    public static final String ONLY_IN_GAME = "§cConsole senders are not allowed to execute this command!";
    public static final String ONLY_CONSOLE = "§cOnly console senders are allowed to execute this command!";
    public static final String PLAYER_NOT_FOUND = "§cPlayer not found.";
    public static final String CONFIG_ERROR = "There is an issue with a configuration entry. Please contact the server's administrator.";
    public static final String MISSING_ARGUMENTS = "§cMissing arguments.";

    AbstractCommand() {
        params = getClass().getAnnotation(CommandParameters.class);
        this.name = getClass().getSimpleName().replace(COMMAND_PREFIX, "");
        this.description = params.description();
        this.usage = params.usage();
        this.alaises = params.aliases();
        this.source = params.source();
    }

    public void register() {
        ACommand cmd = new ACommand(this.name);
        if(this.alaises != null)
            cmd.setAliases(Arrays.asList(this.alaises.split(",")));
        if(this.description != null)
            cmd.setDescription(this.description);
        if(this.usage != null)
            cmd.setUsage(this.usage);
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);

    }

    final CommandMap getCommandMap() {
        if(cmap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(cmap != null) {
            return cmap;
        }
        return getCommandMap();
    }

    private final class ACommand extends org.bukkit.command.Command {
        private AbstractCommand cmd = null;
        private ACommand(String cmd) {
            super(cmd);
        }

        public void setExecutor(AbstractCommand cmd) {
            this.cmd = cmd;
        }

        public boolean execute(CommandSender sender, String c, String[] args) {
            if(cmd != null) {
                cmd.sender = new CommandUser(sender);
                cmd.args = args;

                if(params.source() == CommandSource.IN_GAME && sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(ONLY_IN_GAME);
                    return true;
                }
                if(params.source() == CommandSource.CONSOLE && sender instanceof Player) {
                    sender.sendMessage(ONLY_CONSOLE);
                    return true;
                }

                return cmd.onCommand(sender, this, c, args);
            }
            return false;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
            if (cmd != null)
            {
                return cmd.onTabComplete(sender, this, alias, args);
            }
            return null;
        }
    }

    protected User getDungeonPlayer(String s) {
        Player player = getPlayer(s);
        return new User(player);
    }

    protected void checkArgs(boolean b) {
        if(b)
            throw new CommandFailException("Command failed: Argument Exception");
    }

    protected void failCommand(String s) {
        throw new CommandFailException(s);
    }

    protected Player getPlayer(String s) {
        return Bukkit.getPlayerExact(s);
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String c, String[] args) {
        CommandUser cu = new CommandUser(sender);
        try {
            run(cu, cu.getUser(), args);
        }
        catch (CommandFailException ex) {
            if(ex.getMessage().contains("Argument")) {
                sender.sendMessage(ChatColor.WHITE + cmd.getUsage().replace("<command>", cmd.getLabel()));
            }
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String c, String[] args) {
        List<String> options = getTabCompleteOptions(this.sender, args);
        if (options == null) {
            return IUtil.getPlayerList();
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], options, new ArrayList<>());
    }

    public abstract void run(CommandUser sender, User user, String[] args);

    protected List<String> getTabCompleteOptions(CommandUser sender, String[] args) {
        return IUtil.getPlayerList();
    }
}
