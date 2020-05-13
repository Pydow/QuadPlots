package net.lldv.pydow.quadplots.components.settings;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockIds;

public class PlotSettings {
    public static Block roadBlock;
    public static Block bottomBlock;
    public static Block plotFillBlock;
    public static Block plotFloorBlock;
    public static Block wallBlock;
    public static Block wallBlockClaimed;
    public static String world = "world";
    public static int roadWidth = 7;
    public static int groundHeight = 64;
    public static int plotSize = 32;

    public static void load() {
        roadBlock = Block.get(BlockIds.PLANKS);
        wallBlock = Block.get(BlockIds.STONE_SLAB);
        plotFloorBlock = Block.get(BlockIds.GRASS);
        plotFillBlock = Block.get(BlockIds.DIRT);
        bottomBlock = Block.get(BlockIds.BEDROCK);
        wallBlockClaimed = Block.get(BlockIds.STONE_SLAB, 4);
        roadWidth = 7;
        plotSize = 42;
        groundHeight = 64;
    }

}
