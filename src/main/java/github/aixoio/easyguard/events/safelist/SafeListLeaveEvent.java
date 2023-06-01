package github.aixoio.easyguard.events.safelist;

import github.aixoio.easyguard.EasyGuard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class SafeListLeaveEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        boolean safeListOn = EasyGuard.getPlugin().getConfig().getBoolean("SAFELIST_ON");

        if (!safeListOn) return;

        Player player = e.getPlayer();

        ArrayList<String> safelistedPlayers = new ArrayList<String>(EasyGuard.getPlugin().getConfig().getStringList("ALLOWED_PLAYERS"));

        if (safelistedPlayers.contains(player.getDisplayName())) return;

        String safeListMode = EasyGuard.getPlugin().getConfig().getString("SAFELIST_MODE");

        if (!safeListMode.equalsIgnoreCase("KICK") &&
                !safeListMode.equalsIgnoreCase("BAN") && !safeListMode.equalsIgnoreCase("LOG_ONLY") &&
                !safeListMode.equalsIgnoreCase("BAN_IP")) return;

        if (safeListMode.equalsIgnoreCase("LOG_ONLY")) return;

        if (player.hasPermission("easyguard.safelist-bypass")) return;

        e.setQuitMessage(null);

    }

}
