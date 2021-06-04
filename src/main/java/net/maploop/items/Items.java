package net.maploop.items;

import net.maploop.items.command.AbstractCommand;
import net.maploop.items.command.CommandHandler;
import net.maploop.items.command.commands.*;
import net.maploop.items.data.AbilityHandler;
import net.maploop.items.data.BackpackData;
import net.maploop.items.enums.AbilityType;
import net.maploop.items.enums.ItemType;
import net.maploop.items.enums.Rarity;
import net.maploop.items.event.PlayerCustomDeathEvent;
import net.maploop.items.gui.PlayerMenuUtility;
import net.maploop.items.item.ItemAbility;
import net.maploop.items.item.SBItems;
import net.maploop.items.item.items.*;
import net.maploop.items.listeners.*;
import net.maploop.items.sql.MySQL;
import net.maploop.items.sql.SQLGetter;
import net.maploop.items.user.User;
import net.maploop.items.user.UserInjector;
import net.maploop.items.util.IReflections;
import net.maploop.items.util.IUtil;
import net.maploop.items.util.PAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public final class Items extends JavaPlugin {
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    public static List<Player> goded = new ArrayList<>();
    public static boolean BlessingTesting = false;
    public static Items plugin;
    public MySQL sql;

    @Override
    public void onEnable() {
        plugin = this;
        createDataFile();
        createBackpackDataFile();
        saveDefaultConfig();
        registerListeners();
        registerCommands();
        registerItems();
        loadCommands();
        createShutsFile();
        checkIfDead();

        if(!Bukkit.getServer().getOnlinePlayers().isEmpty()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                User user = new User(player);
                UserInjector injector = new UserInjector(user);
                injector.inject();
            }
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PAPI().register();
        }

        this.sql = new MySQL(this);
        try {
            this.sql.connect();
            SQLGetter.createTable();
            System.out.println("SQL Successfully connected.");
            for (final Player p : Bukkit.getOnlinePlayers()) {
                final SQLGetter getter = new SQLGetter(p, getInstance());
                getter.inject();
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Connection failed.");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                BackpackData.restore();
            }
        }.runTaskLater(this, 3);

        getServer().getConsoleSender().sendMessage("§aItems was enabled successfully with no fatal errors.");
    }

    @Override
    public void onDisable() {
        BackpackData.save();
        getServer().getConsoleSender().sendMessage("§cItems was disabled.");
    }

    private void checkIfDead(){
        IUtil.scheduleRepeatingTask(() -> {
            if (Bukkit.getOnlinePlayers().size() == 0) return;
            for (Player player : Bukkit.getOnlinePlayers()) {
                User user = new User(player);
                if (user.getHealth() <= 0) {
                    Bukkit.getPluginManager().callEvent(new PlayerCustomDeathEvent(player, user, EntityDamageEvent.DamageCause.ENTITY_ATTACK));
                    user.setHealth(user.getTotalHealth());
                }
            }
        },0,1);
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerUseCustomItem(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemPickupListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerLoseHungerListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerArmorStandManipulateListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
        this.getServer().getPluginManager().registerEvents(new EntityInteractAtEntityListener(), this);
        this.getServer().getPluginManager().registerEvents(new SignGUIUpdateListener(), this);
        this.getServer().getPluginManager().registerEvents(new AbilityHandler(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerCustomDeathListener(), this);
    }

    private void registerItems() {
        SBItems.putItem("aspect_of_the_dragons", new AspectOfTheDragons(1, Rarity.LEGENDARY, "Aspect of the Dragons", Material.DIAMOND_SWORD, 0, false, false, false, Collections.singletonList(new ItemAbility("Dragon Rage", AbilityType.RIGHT_CLICK, "§7All Monsters in front of you\n§7take §aint §7damage. Hit\n§7monsters take large knockback.")), 100, true, ItemType.SWORD, false));
        SBItems.putItem("flower_of_truth", new FlowerOfTruth(2, Rarity.LEGENDARY, "Flower of Truth", Material.RED_ROSE, 0, false, false, false, Collections.singletonList(new ItemAbility("Heat-Seeking Rose", AbilityType.RIGHT_CLICK, "§7Shoots a rose that ricochets\n§7between enemies, damaging up to\n§a3 §7of your foes! Damage\n§7multiplies as more enemies are\n§7hit.", 1)), 0, true, ItemType.DUNGEON_SWORD, true));
        SBItems.putItem("skyblock", new Skyblock(6, Rarity.VERY_SPECIAL, "Skyblock", Material.SKULL_ITEM, 3, false, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50", true));
        SBItems.putItem("bone_boomerang", new Bonemerang(8, Rarity.LEGENDARY, "Bonemerang", Material.BONE, 0, false, false, false, Collections.singletonList(new ItemAbility("Swing", AbilityType.RIGHT_CLICK, "§7Throw the bone for a short distance,\n§7dealing the damage an arrow\n§7would.\n\n§7Deals §cdouble damage §7when\n§7coming back. Pierces up to §e10\n§7foes.", 0)), 0, true, ItemType.DUNGEON_BOW, true));
        SBItems.putItem("builders_wand", new BuildersWand(9, Rarity.LEGENDARY, "Builder's Wand", Material.BLAZE_ROD, 0, false, false, false, Arrays.asList(new ItemAbility("Grand Architect", AbilityType.RIGHT_CLICK, "§7Right-Click the face of a block\n§7to extend all connected block\n§7faces.\n§8Consumes blocks from your inventory!"), new ItemAbility("Built-in Storage", AbilityType.LEFT_CLICK, IUtil.colorize("&7Opens the wand storage. Blocks\n&7will be placed from your\n&7inventory or the wand storage.\n&cTHIS ABILITY IS DISABLED!"))), 0, false, ItemType.ITEM, true));
        SBItems.putItem("infinidirt_wand", new InfiniDirtWand(10, Rarity.UNCOMMON, "InfiniDirt™ Wand", Material.STICK, 0, false, false, false, Collections.singletonList(new ItemAbility("Place Dirt", AbilityType.RIGHT_CLICK, "§7Place a dirt block.\n§7Costs §61 coins§7.\n \n§7Can be used within the Builders\n§7Wand!")), 0, false, ItemType.ITEM, true));
        SBItems.putItem("basket_of_seeds", new BasketOfSeeds(17, Rarity.UNFINISHED, "Basket of Seeds", Material.SKULL_ITEM, 3, true, false, false, Collections.singletonList(new ItemAbility("Farmer's Delight", AbilityType.RIGHT_CLICK, IUtil.colorize("&7Automatically seed a row of\n&7farmland."))), 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/7a6bf916e28ccb80b4ebfacf98686ad6af7c4fb257e57a8cb78c71d19dccb2", false));
        SBItems.putItem("small_backpack", new SmallBackpack(18, Rarity.UNCOMMON, "Small Backpack", Material.SKULL_ITEM, 3, false, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622", false));
        SBItems.putItem("medium_backpack", new MediumBackpack(19, Rarity.RARE, "Medium Backpack", Material.SKULL_ITEM, 3, false, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622", false));
        SBItems.putItem("large_backpack", new LargeBackpack(20, Rarity.EPIC, "Large Backpack", Material.SKULL_ITEM, 3, false, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622", false));
        SBItems.putItem("greater_backpack", new GreaterBackpack(21, Rarity.EPIC, "Greater Backpack", Material.SKULL_ITEM, 3, false, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622", false));
        SBItems.putItem("jumbo_backpack", new JumboBackpack(22, Rarity.LEGENDARY, "Jumbo Backpack", Material.SKULL_ITEM, 3, false, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622", false));
        SBItems.putItem("grappling_hook", new GrapplingHook(23, Rarity.UNCOMMON, "Grappling Hook", Material.FISHING_ROD, 0, true, false, false, null, 0, false, ItemType.ITEM, false));
        SBItems.putItem("magical_clock", new MagicalClock(24, Rarity.EPIC, "Magical Clock", Material.WATCH, 0, false, false, false, null, 0, true, ItemType.ACCESSORY, true));
        SBItems.putItem("block_zapper", new BlockZapper(25, Rarity.EPIC, "Block Zapper", Material.FLINT, 0, false, false, false, Collections.singletonList(new ItemAbility("Grand... Zapper?", AbilityType.RIGHT_CLICK, IUtil.colorize("&7Right-Click a block to zap all\n&7connected blocks of same\n&7type."))), 0, false, ItemType.ITEM, true));
        SBItems.putItem("dwarven_tankard", new DwarvenTankard(26, Rarity.EPIC, "Dwarven Tankard", Material.SKULL_ITEM, 3, true, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/8f7dc8c91f7a24ecd9c5e9d4d8c39f0ac8333ae4853559acb8b03866f9d", false));
        SBItems.putItem("aspect_of_the_jerry", new AspectOfTheJerry(27, Rarity.COMMON, "Aspect of the Jerry", Material.WOOD_SWORD, 0, true, false, false, Collections.singletonList(new ItemAbility("Parley", AbilityType.RIGHT_CLICK, IUtil.colorize("&7Channel your inner parley."), 5)), 0, true, ItemType.SWORD, false, 1, 0, 0, 0, 0, 0));
        SBItems.putItem("shadow_fury", new ShadowFury(28, Rarity.LEGENDARY, "Shadow Fury", Material.DIAMOND_SWORD, 0, true, false, false, Collections.singletonList(new ItemAbility("Shadow Fury", AbilityType.RIGHT_CLICK, IUtil.colorize("&7Rapidly teleports you to up to\n&b5 &7enemies within &e12\n&7blocks, rooting each of them\n&7and allowing you to hit them."), 15)), 0, true, true,ItemType.DUNGEON_SWORD, false, 300, 125, 0, 0, 0, 0));
        SBItems.putItem("scylla", new Scylla(29, Rarity.LEGENDARY, "Scylla", Material.IRON_SWORD, 0, true, false, false, null, 0, true, ItemType.DUNGEON_SWORD, false));
        SBItems.putItem("aspect_of_the_end", new AspectOfTheEnd(30, Rarity.RARE, "Aspect of the End", Material.DIAMOND_SWORD, 0, true, false, false, Collections.singletonList(new ItemAbility("Instant Transmission", AbilityType.RIGHT_CLICK, IUtil.colorize("&7Teleport &a8 blocks &7ahead of\n&7you and gain &a+50 &f✦ Speed\n&7for &a3 seconds&7."))), 50, true, ItemType.SWORD, false));
        SBItems.putItem("bonzo_staff", new BonzoStaff(34, Rarity.RARE, "Bonzo's Staff", Material.BLAZE_ROD, 0, false, false, false, Collections.singletonList(new ItemAbility("Showtime!", AbilityType.RIGHT_CLICK, IUtil.colorize("Shoot balloons that create a\nlarge explosion on impact,\ndealing up to &c1,000 &7damage."))), 60, true, ItemType.DUNGEON_SWORD, false, 160, 0, 0, 0, 0, 0));
        SBItems.putItem("bonzos_balloon", new BonzosBalloon(35, Rarity.UNOBTAINABLE, "Bonzo's Balloon", Material.SKULL_ITEM, 3, true, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/76387fc246893d92a6dd9ea1b52dcd581e991eeee2e263b27fff1bcf1b154eb7", false));
        SBItems.putItem("helmet_of_growth", new HelmetOfGrowth(36, Rarity.EPIC, "Helmet of Growth", Material.GOLD_HELMET, 0, true, false, false ,null, 0, false, ItemType.HELMET, true, 0, 0, 0, 20, 120, 500));
        SBItems.putItem("guided_missile", new GuidedMissile(37, Rarity.LEGENDARY, "Guided Missile", Material.SKULL_ITEM, 3, true, false, false, Collections.singletonList(new ItemAbility("Guided Missile", AbilityType.RIGHT_CLICK, IUtil.colorize("Shoots a guided missile. Upon shooting,\nyour game camera will\nlatch onto the missile, allowing\nyou to control it. The\nmissile explodes for &c10,000 &7damage."), 30)), 250, false, ItemType.ITEM, "http://textures.minecraft.net/texture/315bbda12e1b832a6a6af85d8439152d9157ce104e6a7f7b36aeaccc863544", false));
        SBItems.putItem("ember_rod", new EmberRod(38, Rarity.EPIC, "Ember Rod", Material.BLAZE_ROD, 0, false, false, false, Collections.singletonList(new ItemAbility("Fire Blast", AbilityType.RIGHT_CLICK, IUtil.colorize("&7Shoot 3 Fireballs in rapid\nsuccession in front of you!"), 30)), 150, true, true, ItemType.ITEM, true, 80, 35, 0, 0, 0 ,0));
        SBItems.putItem("jerrychine_gun", new JerrychineGun(39, Rarity.EPIC, "Jerry-chine Gun", Material.GOLD_BARDING, 0, true, false, false, Collections.singletonList(new ItemAbility("Rapid-fire", AbilityType.RIGHT_CLICK, "Fire off multiple jerry bombs\nthat create an explosion on\nimpact, dealing up to §c5,000§7\ndamage.")), 10, true, true, ItemType.DUNGEON_SWORD, false, 80, 0, 0, 200, 0, 0));
        SBItems.putItem("jerry_head", new JerryHead(40, Rarity.UNOBTAINABLE, "Beheaded Jerry", Material.SKULL_ITEM, 3, true, false, false, null, 0, false, ItemType.ITEM, "http://textures.minecraft.net/texture/41b830eb4082acec836bc835e40a11282bb51193315f91184337e8d3555583", false));
        SBItems.putItem("hyperion", new Hyperion(41, Rarity.LEGENDARY, "Hyperion", Material.IRON_SWORD, 0, true, false, false, Collections.singletonList(new ItemAbility("Wither Impact", AbilityType.RIGHT_CLICK, "Teleport §a10 blocks §7ahead of\nyou. Then implode dealing\n§c10,000 §7damage to nearby\nenemies. Also applies the wither\nshield scroll ability reducing\ndamage taken and granting an absorption shield for §e5§7\nseconds.")), 250, true, true, ItemType.DUNGEON_SWORD, false, 260, 150, 0, 350, 0, 0));
        SBItems.putItem("danteSoul", new DanteSoul());
    }

    private void registerCommands() {
        this.getCommand("item").setExecutor(new Command_item());
        this.getCommand("items").setExecutor(new Command_items());
        this.getCommand("e").setExecutor(new Command_items());
        this.getCommand("undozap").setExecutor(new Command_undozap());
        this.getCommand("undograndarchitect").setExecutor(new Command_undograndarchitect());
        this.getCommand("mcitems").setExecutor(new Command_mcitems());
        this.getCommand("dyeitem").setExecutor(new Command_dyeitems());
        this.getCommand("gm").setExecutor(new Command_gamemode());
        this.getCommand("gamemode").setExecutor(new Command_gamemode());
        this.getCommand("sbclear").setExecutor(new Command_sbclear());
        this.getCommand("clear").setExecutor(new Command_sbclear());
        this.getCommand("cl").setExecutor(new Command_sbclear());
        this.getCommand("setspawn").setExecutor(new Command_setspawn());
        this.getCommand("loop").setExecutor(new Command_loop());
        this.getCommand("createitem").setExecutor(new Command_createitem());
        this.getCommand("placespecialanvil").setExecutor(new Command_placespecialanvil());
        this.getCommand("recombobulate").setExecutor(new Command_recombobulate());
        this.getCommand("itemssetlobby").setExecutor(new Command_setlobby());
        this.getCommand("shutup").setExecutor(new Command_shutup());
        this.getCommand("stfu").setExecutor(new Command_shutup());
        this.getCommand("piano").setExecutor(new Command_piano());
        this.getCommand("rainbow").setExecutor(new Command_rainbow());
        this.getCommand("wardrobe").setExecutor(new Command_wardrobe());
    }

    private void loadCommands() {
        List<Class<? extends AbstractCommand>> cmds = IReflections.getSubclassesInPackage("net.maploop.items.command.commands", AbstractCommand.class);
        if(cmds != null) {
            for(Class<? extends AbstractCommand> c : cmds) {
                try {
                    new CommandHandler().add(c.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(player))) {

            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(player);
        }
    }

    public static Items getInstance() {
        return  plugin;
    }

    private File customConfigFile;
    private FileConfiguration customConfig;

    public FileConfiguration getData() {
        return this.customConfig;
    }

    public void saveData() {
        try {
            this.customConfig.save(customConfigFile);
        } catch (IOException e) {
            System.out.println("An error occurred while saving data! [WARN] [CRITICAL]");
        }
    }

    private void createDataFile() {
        customConfigFile = new File(getDataFolder(), "data.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private File backpackDataFile;
    private FileConfiguration backpackData;

    public FileConfiguration getBackpackData() { return this.backpackData; }
    public void saveBackbackData() {
        try {
            this.backpackData.save(backpackDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBackpackDataFile() {
        backpackDataFile = new File(getDataFolder(), "backpack_data.yml");
        if(!backpackDataFile.exists()) {
            backpackDataFile.getParentFile().mkdirs();
            saveResource("backpack_data.yml", false);
        }
        backpackData = new YamlConfiguration();
        try {
            backpackData.load(backpackDataFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private File shuts;
    private FileConfiguration shutsConfig;

    public FileConfiguration getShuts() {
        return this.shutsConfig;
    }

    public void saveShuts() {
        try {
            this.shutsConfig.save(shuts);
        } catch (IOException e) {
            System.out.println("An error occurred while saving data! [WARN] [CRITICAL]");
        }
    }

    private void createShutsFile() {
        shuts = new File(getDataFolder(), "shuts.yml");
        if (!shuts.exists()) {
            shuts.getParentFile().mkdirs();
            saveResource("shuts.yml", false);
        }

        shutsConfig= new YamlConfiguration();
        try {
            shutsConfig.load(shuts);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
