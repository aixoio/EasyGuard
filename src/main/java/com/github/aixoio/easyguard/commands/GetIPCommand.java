package com.github.aixoio.easyguard.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetIPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("easyguard.get-ip")) {

            sender.sendMessage(ChatColor.DARK_RED + "You cannot run this command!");
            return true;

        }

        if (args.length < 1) return false;

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) return false;

        String ip = target.getAddress().getAddress().getHostName().replaceFirst("localhost", "127.0.0.1");

        sender.sendMessage(ChatColor.GREEN + "The ip of " + ChatColor.LIGHT_PURPLE + target.getDisplayName() + ChatColor.GREEN + " is " + ChatColor.LIGHT_PURPLE + ip + ChatColor.GREEN + "!");

        return true;

    }

}
