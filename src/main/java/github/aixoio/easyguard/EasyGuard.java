package github.aixoio.easyguard;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGuard extends JavaPlugin {


    private static EasyGuard PLUGIN;

    @Override
    public void onEnable() {

        this.getLogger().info("Starting...");

        EasyGuard.PLUGIN = this;

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
