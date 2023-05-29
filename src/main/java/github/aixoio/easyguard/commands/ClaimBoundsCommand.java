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

            world.spawnParticle(Particle.REDSTONE, locat1, EasyGuard.getPlugin().getConfig().getInt("MASTER_POINT_PARTICLE_COUNT"), dust);

            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat1.getY(), locat2.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat2.getY(), locat2.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat2.getY(), locat1.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat2.getY(), locat1.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);

            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat2.getY(), locat2.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat1.getY(), locat2.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat2.getX(), locat1.getY(), locat1.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);
            world.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), locat1.getX(), locat1.getY(), locat1.getZ()), EasyGuard.getPlugin().getConfig().getInt("POINT_PARTICLE_COUNT"), dust);

            world.spawnParticle(Particle.REDSTONE, locat2, EasyGuard.getPlugin().getConfig().getInt("MASTER_POINT_PARTICLE_COUNT"), dust);

            // NOTE: Draw points - END DO NOT CHANGE

            dust = new Particle.DustOptions(Color.GRAY, 2.5f);

            final double BASE_SEED = 0.5;
            final int COUNT = EasyGuard.getPlugin().getConfig().getInt("POINT_LINE_PARTICLE_COUNT");
            final double RANGE = 0.1;

            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat1.getY(), locat1.getZ()),
                    new Location(world, locat1.getX(), locat2.getY(), locat1.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat1.getY(), locat1.getZ()),
                    new Location(world, locat2.getX(), locat1.getY(), locat1.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat1.getY(), locat1.getZ()),
                    new Location(world, locat1.getX(), locat1.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);


            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat2.getY(), locat1.getZ()),
                    new Location(world, locat2.getX(), locat2.getY(), locat1.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat2.getY(), locat1.getZ()),
                    new Location(world, locat1.getX(), locat2.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat2.getX(), locat1.getY(), locat2.getZ()),
                    new Location(world, locat2.getX(), locat2.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat2.getX(), locat1.getY(), locat1.getZ()),
                    new Location(world, locat2.getX(), locat2.getY(), locat1.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat1.getY(), locat2.getZ()),
                    new Location(world, locat1.getX(), locat2.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat2.getY(), locat2.getZ()),
                    new Location(world, locat2.getX(), locat2.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat2.getX(), locat2.getY(), locat1.getZ()),
                    new Location(world, locat2.getX(), locat2.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat1.getX(), locat1.getY(), locat2.getZ()),
                    new Location(world, locat2.getX(), locat1.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);

            this.drawLineBetweenParticles(
                    new Location(world, locat2.getX(), locat1.getY(), locat1.getZ()),
                    new Location(world, locat2.getX(), locat1.getY(), locat2.getZ()),
                    BASE_SEED, world, dust, COUNT, RANGE);


            sender.sendMessage(String.format("%sYou are currently in %s%s%s", ChatColor.BLUE, ChatColor.GOLD, ChatColor.BOLD, region.getId()));

        }

        if (applicableRegionSet.size() == 0) {

            sender.sendMessage(ChatColor.RED + "You are not inside a claim right now!");

        }

        return true;

    }

    private void drawLineBetweenParticles(Location current, Location target, final double BASE_SPEED, World world, Particle.DustOptions dust, int count, double range) {

        double diffX = (current.getX() - target.getX());
        double diffY = (current.getY() - target.getY());
        double diffZ = (current.getZ() - target.getZ());

        double distance = current.distance(target);

        final double SPEED = -BASE_SPEED;

        double xv = ((SPEED / distance) * diffX);
        double yv = ((SPEED / distance) * diffY);
        double zv = ((SPEED / distance) * diffZ);

        double x = current.getX();
        double y = current.getY();
        double z = current.getZ();

        while (Math.abs(x - target.getX()) > range || Math.abs(y - target.getY()) > range || Math.abs(z - target.getZ()) > range) {

            world.spawnParticle(Particle.REDSTONE, new Location(world, x, y, z), count, dust);

            x += xv;
            y += yv;
            z += zv;

        }

    }

}
