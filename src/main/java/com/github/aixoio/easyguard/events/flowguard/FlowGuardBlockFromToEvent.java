package com.github.aixoio.easyguard.events.flowguard;

import com.github.aixoio.easyguard.EasyGuard;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class FlowGuardBlockFromToEvent implements Listener {

    @EventHandler
    public void onBlockFromToEvent(BlockFromToEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("FLOWGUARD_ACTIVE");

        if (!active) return;

        if (!e.getBlock().isLiquid()) return;

        BlockVector3 location = BlockVector3.at(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ());

        ApplicableRegionSet applicableRegionSet = WorldGuard
                .getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(BukkitAdapter.adapt(e.getBlock().getWorld()))
                .getApplicableRegions(
                        location
                );

        LinkedList<ProtectedRegion> regions = new LinkedList<ProtectedRegion>();

        regions.addAll(applicableRegionSet.getRegions());

        for (byte i = 0; i < 3; i++) {

            double x = e.getBlock().getLocation().getX();

            if (i == 0) x++;
            if (i == 1) x--;

            location = BlockVector3.at(x, e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ());

            applicableRegionSet = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(BukkitAdapter.adapt(e.getBlock().getWorld()))
                    .getApplicableRegions(
                            location
                    );

            regions.addAll(applicableRegionSet.getRegions());

        }

        for (byte i = 0; i < 3; i++) {

            double y = e.getBlock().getLocation().getY();

            if (i == 0) y++;
            if (i == 1) y--;

            location = BlockVector3.at(e.getBlock().getLocation().getX(), y, e.getBlock().getLocation().getZ());

            applicableRegionSet = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(BukkitAdapter.adapt(e.getBlock().getWorld()))
                    .getApplicableRegions(
                            location
                    );

            regions.addAll(applicableRegionSet.getRegions());

        }

        for (byte i = 0; i < 3; i++) {

            double z = e.getBlock().getLocation().getZ();

            if (i == 0) z++;
            if (i == 1) z--;

            location = BlockVector3.at(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(), z);

            applicableRegionSet = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(BukkitAdapter.adapt(e.getBlock().getWorld()))
                    .getApplicableRegions(
                            location
                    );

            regions.addAll(applicableRegionSet.getRegions());

        }

        Set<ProtectedRegion> regionSet = new HashSet<>(regions);

        boolean anyhaswaterflow = false;
        boolean anyhaslavaflow = false;

        for (ProtectedRegion region : regionSet) {

            StateFlag flag = Flags.WATER_FLOW;

            if (region.getFlag(flag) == StateFlag.State.DENY) {

                anyhaswaterflow = true;
                break;

            }

        }

        for (ProtectedRegion region : regionSet) {

            StateFlag flag = Flags.LAVA_FLOW;

            if (region.getFlag(flag) == StateFlag.State.DENY) {

                anyhaslavaflow = true;
                break;

            }

        }

        if (e.getBlock().getType() == Material.LAVA && anyhaslavaflow) e.setCancelled(true);
        if (e.getBlock().getType() == Material.WATER && anyhaswaterflow) e.setCancelled(true);

    }

}
