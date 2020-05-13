package net.lldv.pydow.quadplots.listener;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.LiquidFlowEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.ItemEdible;
import cn.nukkit.level.Location;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

import java.util.ArrayList;
import java.util.List;

public class BlockListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        if (!event.getBlock().getLevel().getName().equalsIgnoreCase(PlotSettings.world)) return;
        if (QuadPlots.instance.isBypassing(event.getPlayer().getName())) return;

        PlotCallback cb = QuadPlots.instance.getPlotByPosition(Location.from(event.getBlock().getPosition(), event.getBlock().getLevel()));
        if (cb.plot != null) {
            if (!cb.plot.hasBuildPermission(event.getPlayer().getName())) event.setCancelled(true);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        if (!event.getBlock().getLevel().getName().equalsIgnoreCase(PlotSettings.world)) return;
        if (QuadPlots.instance.isBypassing(event.getPlayer().getName())) return;

        PlotCallback cb = QuadPlots.instance.getPlotByPosition(Location.from(event.getBlock().getPosition(), event.getBlock().getLevel()));
        if (cb.plot != null) {
            if (!cb.plot.hasBuildPermission(event.getPlayer().getName())) event.setCancelled(true);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (!event.getBlock().getLevel().getName().equalsIgnoreCase(PlotSettings.world)) return;
        if ((event.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_AIR || event.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) && event.getItem() instanceof ItemEdible) return;
        if (QuadPlots.instance.isBypassing(event.getPlayer().getName())) return;

        PlotCallback cb = QuadPlots.instance.getPlotByPosition(Location.from(event.getBlock().getPosition(), event.getBlock().getLevel()));
        if (cb.plot != null) {
            if (!cb.plot.hasBuildPermission(event.getPlayer().getName())) event.setCancelled(true);
        } else event.setCancelled(true);

    }

    // ToDo: fix this shit
    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {

        if (!event.getEntity().getLevel().getName().equalsIgnoreCase(PlotSettings.world)) return;

        event.setBlockList(new ArrayList<>());
        /*
        if (event.isCancelled()) return;
        String levelName = event.getEntity().getLevel().getName();
        PlotCallback plot = QuadPlots.instance.getPlotByPosition(event.getPosition());
        if (plot.plot == null) {
            event.setCancelled(true);
            return;
        }
        Location plotPos = QuadPlots.instance.getPlotPosition(plot.plot);
        Location beginPos = plotPos.setComponents(plotPos.x, 256, plotPos.z);
        Location endPos = beginPos.setComponents(beginPos.x + PlotSettings.plotSize, 0, beginPos.z + PlotSettings.plotSize);
        List<Block> allowedBlocks = new ArrayList<>();
        for (Block exploded : event.getBlockList()) {
            if(PlotMath.isBetween(exploded.getLocation(), beginPos, endPos)) allowedBlocks.add(exploded);
        }
        event.setBlockList(allowedBlocks);*/
    }

    @EventHandler
    public void onLiquid(LiquidFlowEvent event) {
        if (event.isCancelled()) return;

        String level = event.getBlock().getLevel().getName();
        if (!level.equalsIgnoreCase(PlotSettings.world)) return;

        PlotCallback cb = QuadPlots.instance.getPlotByPosition(Location.from(event.getTo().getPosition(), event.getBlock().getLevel()));
        if (cb.plot == null) event.setCancelled(true);
    }



}
