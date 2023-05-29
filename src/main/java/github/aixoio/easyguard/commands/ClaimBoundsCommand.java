package github.aixoio.easyguard.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import github.aixoio.easyguard.EasyGuard;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ClaimBoundsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command!");
            return true;

        }

        Player player = (Player) sender;

        if (!player.hasPermission("easyguard.bounds")) {

            sender.sendMessage(ChatColor.DARK_RED + "You do not have the needed permission to use this command!");
            return true;

        }

        LocalPlayer localPlayer = EasyGuard.getWorldGuard().wrapPlayer(player);

        BlockVector3 location = BlockVector3.at(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        ApplicableRegionSet applicableRegionSet = WorldGuard
                .getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(localPlayer.getWorld())
                .getApplicableRegions(
                        location
                );

        for (ProtectedRegion region : applicableRegionSet) {

            Location locat1 = new Location(player.getWorld(), region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
            Location locat2 = new Location(player.getWorld(), region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ());

            World world = player.getWorld();

            Particle.DustOptions dust = new Particle.DustOptions(Color.YELLOW, 5f);

            // NOTE: Draw points - START DO NOT CHANGE

            world.spawnParticle(Particle.REDSTONE, locat1, 200, dust);

            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat1.getY(), locat2.getZ()), 200, dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat2.getY(), locat2.getZ()), 200, dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat2.getY(), locat1.getZ()), 200, dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat2.getY(), locat1.getZ()), 200, dust);

            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat2.getY(), locat2.getZ()), 200, dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat1.getY(), locat2.getZ()), 200, dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat1.getY(), locat1.getZ()), 200, dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat1.getY(), locat1.getZ()), 200, dust);

            world.spawnParticle(Particle.REDSTONE, locat2, 200, dust);

            // NOTE: Draw points - END DO NOT CHANGE

        }

        if (applicableRegionSet.size() == 0) {

            sender.sendMessage(ChatColor.RED + "You are not inside a claim right now!");

        }

        return true;
    }
}
