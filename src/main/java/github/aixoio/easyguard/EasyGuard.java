package github.aixoio.easyguard;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import github.aixoio.easyguard.commands.ClaimBoundsCommand;
import github.aixoio.easyguard.commands.ClaimCommand;
import github.aixoio.easyguard.commands.ClaimsCommand;
import github.aixoio.easyguard.commands.CurrentClaimCommand;
import github.aixoio.easyguard.commands.DeleteClaimCommand;
import github.aixoio.easyguard.commands.FlagCommand;
import github.aixoio.easyguard.commands.GetIPCommand;
import github.aixoio.easyguard.commands.TrustCommand;
import github.aixoio.easyguard.commands.WhereClaimCommand;
import github.aixoio.easyguard.events.antispam.AntiSpamAsyncPlayerChatEvent;
import github.aixoio.easyguard.events.creeperguard.CreeperGuardDamageEvent;
import github.aixoio.easyguard.events.creeperguard.CreeperGuardEntityExplodeEvent;
import github.aixoio.easyguard.events.endermanguard.EndermanGuardBlockTakeEvent;
import github.aixoio.easyguard.events.flowguard.FlowGuardBlockFromToEvent;
import github.aixoio.easyguard.events.flowguard.FlowGuardSpongeAbsorbEvent;
import github.aixoio.easyguard.events.rekillguard.ReKillGuardDamageEvent;
import github.aixoio.easyguard.events.safelist.SafeListJoinEvent;
import github.aixoio.easyguard.events.safelist.SafeListLeaveEvent;
import github.aixoio.easyguard.events.tntguard.TNTGuardExplosionPrime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class EasyGuard extends JavaPlugin {


    private static EasyGuard PLUGIN;
    public static HashMap<UUID, String> lastMessageAntiSpam = new HashMap<UUID, String>();
    public static HashMap<UUID, Integer> warningsAntiSpam = new HashMap<UUID, Integer>();

    @Override
    public void onEnable() {

        this.getLogger().info("Starting...");

        EasyGuard.PLUGIN = this;


        if (EasyGuard.getWorldGuard() == null) {

            this.getLogger().warning("Disabling plugin because WorldGuard is not installed!");

            this.getServer().getPluginManager().disablePlugin(this);

        }

        if (EasyGuard.getWorldEdit() == null) {

            this.getLogger().warning("Disabling plugin because WorldEdit is not installed!");

            this.getServer().getPluginManager().disablePlugin(this);

        }

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        this.getCommand("claim").setExecutor(new ClaimCommand());
        this.getCommand("claims").setExecutor(new ClaimsCommand());
        this.getCommand("current-claim").setExecutor(new CurrentClaimCommand());
        this.getCommand("trust").setExecutor(new TrustCommand());
        this.getCommand("delete-claim").setExecutor(new DeleteClaimCommand());
        this.getCommand("flag").setExecutor(new FlagCommand());
        this.getCommand("claim-bounds").setExecutor(new ClaimBoundsCommand());
        this.getCommand("get-ip").setExecutor(new GetIPCommand());
        this.getCommand("where-claim").setExecutor(new WhereClaimCommand());

        this.getServer().getPluginManager().registerEvents(new SafeListJoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new SafeListLeaveEvent(), this);

        this.getServer().getPluginManager().registerEvents(new CreeperGuardEntityExplodeEvent(), this);
        this.getServer().getPluginManager().registerEvents(new CreeperGuardDamageEvent(), this);

        this.getServer().getPluginManager().registerEvents(new EndermanGuardBlockTakeEvent(), this);

        this.getServer().getPluginManager().registerEvents(new TNTGuardExplosionPrime(), this);

        this.getServer().getPluginManager().registerEvents(new ReKillGuardDamageEvent(), this);

        this.getServer().getPluginManager().registerEvents(new FlowGuardBlockFromToEvent(), this);
        this.getServer().getPluginManager().registerEvents(new FlowGuardSpongeAbsorbEvent(), this);

        this.getServer().getPluginManager().registerEvents(new AntiSpamAsyncPlayerChatEvent(), this);

        this.getLogger().info("Started");

    }

    @Override
    public void onDisable() {

        this.getLogger().info("Stopping...");

        this.saveConfig();

        this.getLogger().info("Stopped");

    }

    public static WorldGuardPlugin getWorldGuard() {

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {

            return null;

        }

        return (WorldGuardPlugin) plugin;

    }

    public static WorldEditPlugin getWorldEdit() {

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {

            return null;

        }

        return (WorldEditPlugin) plugin;

    }

    public static EasyGuard getPlugin() {

        return EasyGuard.PLUGIN;

    }

    public static String parseString(String in, Player player) {

        String res = in.replace("{black}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.BLACK.getChar())));
        res = res.replace("{gold}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.GOLD.getChar())));
        res = res.replace("{gray}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.GRAY.getChar())));
        res = res.replace("{blue}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.BLUE.getChar())));
        res = res.replace("{green}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.GREEN.getChar())));
        res = res.replace("{aqua}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.AQUA.getChar())));
        res = res.replace("{red}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.RED.getChar())));
        res = res.replace("{light_purple}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.LIGHT_PURPLE.getChar())));
        res = res.replace("{yellow}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.YELLOW.getChar())));
        res = res.replace("{white}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.WHITE.getChar())));
        res = res.replace("{bold}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.BOLD.getChar())));
        res = res.replace("{italic}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.ITALIC.getChar())));
        res = res.replace("{reset}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.RESET.getChar())));
        res = res.replace("{newline}", "\n");
        res = res.replace("{player}", player.getName());

        return res;

    }

    public static String parseString(String in) {

        String res = in.replace("{black}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.BLACK.getChar())));
        res = res.replace("{gold}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.GOLD.getChar())));
        res = res.replace("{gray}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.GRAY.getChar())));
        res = res.replace("{blue}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.BLUE.getChar())));
        res = res.replace("{green}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.GREEN.getChar())));
        res = res.replace("{aqua}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.AQUA.getChar())));
        res = res.replace("{red}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.RED.getChar())));
        res = res.replace("{light_purple}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.LIGHT_PURPLE.getChar())));
        res = res.replace("{yellow}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.YELLOW.getChar())));
        res = res.replace("{white}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.WHITE.getChar())));
        res = res.replace("{bold}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.BOLD.getChar())));
        res = res.replace("{italic}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.ITALIC.getChar())));
        res = res.replace("{reset}", String.format("%s%s", ChatColor.COLOR_CHAR, String.valueOf(ChatColor.RESET.getChar())));
        res = res.replace("{newline}", "\n");

        return res;

    }

}
