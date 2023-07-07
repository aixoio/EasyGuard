package com.github.aixoio.easyguard.events.antispam;

import com.github.aixoio.easyguard.EasyGuard;
import com.github.aixoio.easyguard.util.AntiSpamMode;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class AntiSpamAsyncPlayerChatEvent implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("ANTI_SPAM_ACTIVE");

        if (!active) return;

        UUID uuid = e.getPlayer().getUniqueId();

        if (EasyGuard.lastMessageAntiSpam.get(uuid) == null) {

            EasyGuard.lastMessageAntiSpam.put(uuid, e.getMessage());
            return;

        }

        String lastMsg = EasyGuard.lastMessageAntiSpam.get(uuid);

        if (e.getMessage().equalsIgnoreCase(lastMsg)) {

            EasyGuard.warningsAntiSpam.putIfAbsent(uuid, 0);

            int waring = EasyGuard.warningsAntiSpam.get(uuid);

            waring++;

            if (waring > EasyGuard.getPlugin().getConfig().getInt("ANTI_SPAM_WARNING_COUNT")) {

                AntiSpamMode antiSpamMode = AntiSpamMode.valueOf(EasyGuard.getPlugin().getConfig().getString("ANTI_SPAM_ACTION"));

                Player player = e.getPlayer();

                if (antiSpamMode == AntiSpamMode.KICK) {

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            player.kickPlayer(EasyGuard.parseString("{red}You cannot {bold}spam{reset}{red} in this server{newline}{reset}{gold} Anti-Spam - Easy Guard"));
                            EasyGuard.getPlugin().getLogger().info(String.format("%s%s was kicked for spam!", ChatColor.GOLD, player.getDisplayName()));
                            Bukkit.broadcastMessage(String.format("%s%s was kicked for spam by Anti-Spam - Easy Guard!", ChatColor.GOLD, player.getDisplayName()));

                        }

                    }.runTaskLater(EasyGuard.getPlugin(), 0L);

                }

                if (antiSpamMode == AntiSpamMode.BAN) {

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            player.getServer().getBanList(BanList.Type.NAME).addBan(player.getDisplayName(),
                                    EasyGuard.parseString("{red}You cannot {bold}spam{reset}{red} in this server{newline}{reset}{gold} Anti-Spam - Easy Guard"),
                                    null, "Anti-Spam - Easy Guard");

                            player.kickPlayer(EasyGuard.parseString("{red}You cannot {bold}spam{reset}{red} in this server{newline}{reset}{gold} Anti-Spam - Easy Guard"));
                            EasyGuard.getPlugin().getLogger().info(String.format("%s%s was kicked for spam!", ChatColor.GOLD, player.getDisplayName()));
                            Bukkit.broadcastMessage(String.format("%s%s was banned for spam by Anti-Spam - Easy Guard!", ChatColor.GOLD, player.getDisplayName()));

                        }

                    }.runTaskLater(EasyGuard.getPlugin(), 0L);

                }

                if (antiSpamMode == AntiSpamMode.BAN_IP) {

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            player.getServer().getBanList(BanList.Type.IP).addBan(player.getAddress().getHostName().replaceFirst("localhost", "127.0.0.1"),
                                    EasyGuard.parseString("{red}You cannot {bold}spam{reset}{red} in this server{newline}{reset}{gold} Anti-Spam - Easy Guard"),
                                    null, "Anti-Spam - Easy Guard");

                            player.kickPlayer(EasyGuard.parseString("{red}You cannot {bold}spam{reset}{red} in this server{newline}{reset}{gold} Anti-Spam - Easy Guard"));
                            EasyGuard.getPlugin().getLogger().info(String.format("%s%s was kicked for spam!", ChatColor.GOLD, player.getDisplayName()));
                            Bukkit.broadcastMessage(String.format("%s%s was IP banned for spam by Anti-Spam - Easy Guard!", ChatColor.GOLD, player.getDisplayName()));

                        }

                    }.runTaskLater(EasyGuard.getPlugin(), 0L);

                }

                if (antiSpamMode == AntiSpamMode.LOG_ONLY) {

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            EasyGuard.getPlugin().getLogger().info(String.format("%s%s was spamming!", ChatColor.GOLD, player.getDisplayName()));
                            Bukkit.broadcastMessage(String.format("%s%s was spamming this is a warning by Anti-Spam - Easy Guard!", ChatColor.GOLD, player.getDisplayName()));

                        }

                    }.runTaskLater(EasyGuard.getPlugin(), 0L);

                }

                e.setCancelled(true);

            } else {

                EasyGuard.warningsAntiSpam.put(uuid, waring);
                e.getPlayer().sendMessage(ChatColor.AQUA + "Repeat message muted");

                if (waring == EasyGuard.getPlugin().getConfig().getInt("ANTI_SPAM_WARNING_COUNT")) {

                    e.getPlayer().sendMessage(ChatColor.GOLD + "Please don't spam this is warning number: " + ChatColor.RED +
                            ChatColor.BOLD + waring + ChatColor.RESET + ChatColor.GOLD + "!" + ChatColor.RED + ChatColor.BOLD + " FINAL WARNING!");

                } else {

                    e.getPlayer().sendMessage(ChatColor.GOLD + "Please don't spam this is warning number: " + ChatColor.RED + ChatColor.BOLD + waring + ChatColor.RESET + ChatColor.GOLD + "!");

                }

                EasyGuard.getPlugin().getLogger().info(String.format("%s%s had a message muted for spam!", ChatColor.GOLD, e.getPlayer().getDisplayName()));

                e.setCancelled(true);

            }

        } else {

            EasyGuard.warningsAntiSpam.put(uuid, 0);
            EasyGuard.lastMessageAntiSpam.put(uuid, e.getMessage());

        }

    }

}
