package net.lldv.pydow.quadplots.components.generator;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.chunk.IChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.GeneratorFactory;
import net.daporkchop.lib.random.PRandom;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

import java.util.HashMap;

public class QuadPlotGen implements Generator, GeneratorFactory {

    public Block roadBlock = PlotSettings.roadBlock;
    public Block bottomBlock = PlotSettings.bottomBlock;
    public Block plotFillBlock = PlotSettings.plotFillBlock;
    public Block plotFloorBlock = PlotSettings.plotFloorBlock;
    public Block wallBlock = PlotSettings.wallBlock;
    public int roadWidth = PlotSettings.roadWidth;
    public int groundHeight = PlotSettings.groundHeight;
    public int plotSize = PlotSettings.plotSize;

    public final int PLOT = 0;
    public final int ROAD = 1;
    public final int WALL = 2;

    @Override
    public void generate(PRandom pRandom, IChunk chunk, int chunkX, int chunkZ) {
        HashMap<Integer, Integer> shape = getShape(chunkX << 4, chunkZ << 4);

        for (int Z = 0; Z < 16; ++Z) {
            for (int X = 0; X < 16; ++X) {
                chunk.setBlock(X, 0, Z, bottomBlock);
                for (int y = 1; y < groundHeight; ++y) chunk.setBlock(X, y, Z, plotFillBlock);
                int type = shape.get((Z << 4) | X);
                if (type == PLOT) {
                    chunk.setBlock(X, groundHeight, Z, plotFloorBlock);
                } else if (type == ROAD) {
                    chunk.setBlock(X, groundHeight, Z, roadBlock);
                } else {
                    chunk.setBlock(X, groundHeight, Z,roadBlock);
                    chunk.setBlock(X, groundHeight + 1, Z, wallBlock);
                }
            }
        }
    }

    @Override
    public void populate(PRandom pRandom, ChunkManager chunkManager, int i, int i1) { }

    public HashMap<Integer, Integer> getShape(int x, int z) {
        int totalSize = plotSize + roadWidth;
        int X, Z, typeZ, typeX, type;

        if (x >= 0) {
            X = x % totalSize;
        } else X = totalSize - Math.abs(x % totalSize);

        if (z >= 0) {
            Z = z % totalSize;
        } else Z = totalSize - Math.abs(z % totalSize);

        int startX = X;
        HashMap<Integer, Integer> shape = new HashMap<>();

        for (z = 0; z < 16; z++, Z++) {
            if (Z == totalSize) Z = 0;
            if (Z < plotSize) {
                typeZ = PLOT;
            } else if (Z == plotSize || Z == (totalSize - 1)) {
                typeZ = WALL;
            } else {
                typeZ = ROAD;
            }
            for (x = 0, X = startX; x < 16; x++, X++) {
                if (X == totalSize) X = 0;
                if (X < plotSize) {
                    typeX = PLOT;
                } else if (X == plotSize || X == (totalSize - 1)) {
                    typeX = WALL;
                } else typeX = ROAD;

                if (typeX == typeZ) {
                    type = typeX;
                } else if (typeX == PLOT) {
                    type = typeZ;
                } else if (typeZ == PLOT) {
                    type = typeX;
                } else type = ROAD;

                shape.put((z << 4) | x, type);
            }
        }
        return shape;
    }

    @Override
    public void finish(PRandom pRandom, ChunkManager chunkManager, int i, int i1) { }

    @Override
    public Generator create(long l, String s) {
        return this;
    }
}
