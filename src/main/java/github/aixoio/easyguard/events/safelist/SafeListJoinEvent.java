package github.aixoio.easyguard.events.safelist;

import github.aixoio.easyguard.EasyGuard;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class SafeListJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        boolean safeListOn = EasyGuard.getPlugin().getConfig().getBoolean("SAFELIST_ON");

        if (!safeListOn) return;

        String safeListMode = EasyGuard.getPlugin().getConfig().getString("SAFELIST_MODE");

        if (!safeListMode.equalsIgnoreCase("KICK") &&
                !safeListMode.equalsIgnoreCase("BAN") && !safeListMode.equalsIgnoreCase("LOG_ONLY") &&
                !safeListMode.equalsIgnoreCase("BAN_IP")) return;

        Player player = e.getPlayer();

        ArrayList<String> safelistedPlayers = new ArrayList<String>(EasyGuard.getPlugin().getConfig().getStringList("ALLOWED_PLAYERS"));

        if (safelistedPlayers.contains(player.getDisplayName())) return;

        if (player.hasPermission("easyguard.safelist-bypass")) {

            EasyGuard.getPlugin().getLogger().info(String.format("%s%s has tried to join but has bypassed the safe-list!", ChatColor.YELLOW, player.getDisplayName()));
            return;

        }

        EasyGuard.getPlugin().getLogger().info(String.format("%s%s has tried to join but is not on the safe-list!", ChatColor.RED, player.getDisplayName()));

        if (!(safeListMode.equalsIgnoreCase("LOG_ONLY"))) {

            e.setJoinMessage(null);

        }

        if (safeListMode.equalsIgnoreCase("KICK")) {

            player.kickPlayer(EasyGuard.parseString(
                    EasyGuard.getPlugin().getConfig().getString("SAFELIST_MESSAGE")
            ));

        }

        if (safeListMode.equalsIgnoreCase("BAN")) {

            player.getServer().getBanList(BanList.Type.NAME).addBan(player.getDisplayName(),
                    EasyGuard.parseString(EasyGuard.getPlugin().getConfig().getString("SAFELIST_MESSAGE")),
                    null,
                    "Easy Guard - Safe-list");

            player.kickPlayer(EasyGuard.parseString(
                    EasyGuard.getPlugin().getConfig().getString("SAFELIST_MESSAGE")
            ));

        }

        if (safeListMode.equalsIgnoreCase("BAN_IP")) {

            player.getServer().getBanList(BanList.Type.IP).addBan(player.getAddress().getHostName().replaceFirst("localhost", "127.0.0.1"),
                    EasyGuard.parseString(EasyGuard.getPlugin().getConfig().getString("SAFELIST_MESSAGE")),
                    null,
                    "Easy Guard - Safe-list");

            player.kickPlayer(EasyGuard.parseString(
                    EasyGuard.getPlugin().getConfig().getString("SAFELIST_MESSAGE")
            ));

        }

    }

}
