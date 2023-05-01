package github.aixoio.easyguard;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import github.aixoio.easyguard.commands.ClaimCommand;
import github.aixoio.easyguard.commands.ClaimsCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGuard extends JavaPlugin {


    private static EasyGuard PLUGIN;

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

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        this.getCommand("claim").setExecutor(new ClaimCommand());
        this.getCommand("claims").setExecutor(new ClaimsCommand());

        this.getLogger().info("Started");

    }

    @Override
    public void onDisable() {

        this.getLogger().info("Stopping...");

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

}
