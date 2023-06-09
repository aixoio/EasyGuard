package github.aixoio.easyguard.events.infobook;

import github.aixoio.easyguard.EasyGuard;
import github.aixoio.easyguard.util.InfoBooks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class InfoBookFirstJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("GIVE_BOOK_ON_FIRST_JOIN");

        if (!active) return;
        if (e.getPlayer().hasPlayedBefore()) return;

        e.getPlayer().getInventory().addItem(InfoBooks.getPlayerBook());

    }

}
