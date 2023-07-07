package com.github.aixoio.easyguard.events.flowguard;

import com.github.aixoio.easyguard.EasyGuard;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SpongeAbsorbEvent;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class FlowGuardSpongeAbsorbEvent implements Listener {

    @EventHandler
    public void onSpongeAbsorbEvent(SpongeAbsorbEvent e) {

        boolean active = EasyGuard.getPlugin().getConfig().getBoolean("FLOWGUARD_ACTIVE");

        if (!active) return;

        LinkedList<ProtectedRegion> regions = new LinkedList<ProtectedRegion>();

        BlockVector3 location = BlockVector3.at(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ());

        ApplicableRegionSet applicableRegionSet = WorldGuard
                .getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(BukkitAdapter.adapt(e.getBlock().getWorld()))
                .getApplicableRegions(
                        location
                );

        regions.addAll(applicableRegionSet.getRegions());

        for (BlockState blockState : e.getBlocks()) {

            Block block = blockState.getBlock();

            location = BlockVector3.at(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ());

            applicableRegionSet = WorldGuard
                    .getInstance()
                    .getPlatform()
                    .getRegionContainer()
                    .get(BukkitAdapter.adapt(block.getWorld()))
                    .getApplicableRegions(
                            location
                    );

            regions.addAll(applicableRegionSet.getRegions());

        }

        Set<ProtectedRegion> regionSet = new HashSet<ProtectedRegion>(regions);

        boolean anyhaswaterflow = false;

        for (ProtectedRegion region : regionSet) {

            StateFlag flag = Flags.WATER_FLOW;

            if (region.getFlag(flag) == StateFlag.State.DENY) {

                anyhaswaterflow = true;
                break;

            }

        }

        if (anyhaswaterflow) e.setCancelled(true);

    }

}
