package net.lldv.pydow.quadplots.components.tasks;

import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import com.nukkitx.math.vector.Vector3i;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.math.PlotVector3i;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

import java.util.concurrent.CompletableFuture;

public class SetBorderTask extends Thread{

    private Plot $plot;
    private Level $level;
    private int $height;
    private Block $plotWallBlock;
    private PlotVector3i $plotBeginPos;
    private double $xMax, $zMax;
    //private $plot, $level, $height, $plotWallBlock, $plotBeginPos, $xMax, $zMax;

    public SetBorderTask (Plot $plot, Block $block) {
        this.$plot = $plot;
        Location plotPos = QuadPlots.instance.getPlotPosition($plot);
        this.$level = plotPos.getLevel();
        this.$plotBeginPos = new PlotVector3i((int) plotPos.getX(), (int) plotPos.getY(), (int) plotPos.getZ()).subtract(1, 0, 1);//$plotBeginPos.subtract(1,0,1);
        //$plotLevel = $plugin->getLevelSettings($plot->levelName);
        int $plotSize = PlotSettings.plotSize;
        this.$xMax = $plotBeginPos.x + $plotSize + 1;
        this.$zMax = $plotBeginPos.z + $plotSize + 1;
        this.$height = PlotSettings.groundHeight;
        this.$plotWallBlock = $block;
    }

    @Override
    public void run() {
        CompletableFuture.runAsync(() -> {
            try {
                double $x;
                double $z;

                for ($x = $plotBeginPos.x; $x <= $xMax; $x++) {
                    $level.setBlock(Vector3i.from($x, $height + 1, $plotBeginPos.z), $plotWallBlock);
                    $level.setBlock(Vector3i.from($x, $height + 1, $zMax), $plotWallBlock);
                }
                for ($z = $plotBeginPos.z; $z <= $zMax; $z++) {
                    $level.setBlock(Vector3i.from($plotBeginPos.x, $height + 1, $z), $plotWallBlock);
                    $level.setBlock(Vector3i.from($xMax, $height + 1, $z), $plotWallBlock);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

}
