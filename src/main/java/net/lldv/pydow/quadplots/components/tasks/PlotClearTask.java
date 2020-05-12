package net.lldv.pydow.quadplots.components.tasks;

import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.math.PlotVector3i;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

import java.util.concurrent.CompletableFuture;


public class PlotClearTask extends Thread {

    private Plot $plot;
    private Level $level;
    private int $height;
    private Block $bottomBlock;
    private Block $plotFillBlock;
    private Block $plotFloorBlock;
    private Location $plotBeginPos;
    private int $xMax;
    private int $zMax;
    private PlotVector3i $pos;

    public PlotClearTask(Plot $plot) {
        this.$plot = $plot;
        $plotBeginPos = QuadPlots.instance.getPlotPosition($plot);
        $level = $plotBeginPos.getLevel();
        //$plotLevel = $plugin->getLevelSettings($plot->levelName);
        int plotSize = PlotSettings.plotSize;
        $xMax = (int) ($plotBeginPos.getX() + plotSize);
        $zMax = (int) ($plotBeginPos.getZ() + plotSize);
        $height = PlotSettings.groundHeight;
        $bottomBlock = PlotSettings.bottomBlock;
        $plotFillBlock = PlotSettings.plotFillBlock;
        $plotFloorBlock = PlotSettings.plotFloorBlock;
        $pos = new PlotVector3i((int) $plotBeginPos.getX(), 0, (int) $plotBeginPos.getZ());
    }

    @Override
    public void run() {
        CompletableFuture.runAsync(() -> {
            try {
                Block $block;
                while ($pos.x < $xMax) {
                    while ($pos.z < $zMax) {
                        while ($pos.y < 256) {
                            if ($pos.y == 0) {
                                $block = $bottomBlock;
                            } else if ($pos.y < $height) {
                                $block = $plotFillBlock;
                            } else if ($pos.y == $height) {
                                $block = $plotFloorBlock;
                            } else {
                                $block = Block.get(0);
                            }
                            if ($pos == null) System.out.println("pos is null");
                            if ($block == null) System.out.println("block is null");
                            if ($level == null) System.out.println("level is null");
                            $level.setBlock($pos.toVector3i(), $block);
                            $pos.y++;
                        }
                        $pos.y = 0;
                        $pos.z++;
                    }
                    $pos.z = (int) $plotBeginPos.getZ();
                    $pos.x++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
