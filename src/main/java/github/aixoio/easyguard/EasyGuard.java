package github.aixoio.easyguard;

import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGuard extends JavaPlugin {

    @Override
    public void onEnable() {

        this.getLogger().info("Starting...");

        this.getLogger().info("Started");

    }

    @Override
    public void onDisable() {

        this.getLogger().info("Stopping...");

        this.getLogger().info("Stopped");

    }
}
