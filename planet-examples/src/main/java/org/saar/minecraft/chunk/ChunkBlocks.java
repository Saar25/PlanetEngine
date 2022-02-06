package org.saar.minecraft.chunk;

import org.saar.minecraft.Block;
import org.saar.minecraft.BlockContainer;
import org.saar.minecraft.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkBlocks {

    private final Block[] blocks = new Block[16 * 16 * 256];

    private final List<BlockContainer> opaque = new ArrayList<>();
    private final List<BlockContainer> water = new ArrayList<>();

    public ChunkBlocks() {
        Arrays.fill(this.blocks, Blocks.AIR);
    }

    private static int index(int x, int y, int z) {
        return ((x & 0xF) << 12) | ((z & 0xF) << 8) | (y & 0xFF);
    }

    public void setBlock(int x, int y, int z, Block block) {
        final int index = index(x, y, z);
        if (this.blocks[index] != Blocks.AIR) {
            this.opaque.removeIf(bc -> bc.
                    getPosition().equals(x, y, z));
            this.water.removeIf(bc -> bc.
                    getPosition().equals(x, y, z));
        }

        if (block == Blocks.WATER) {
            this.water.add(new BlockContainer(x, y, z, block));
        } else if (block != Blocks.AIR) {
            this.opaque.add(new BlockContainer(x, y, z, block));
        }
        this.blocks[index] = block;
    }

    public Block getBlock(int x, int y, int z) {
        final int index = index(x, y, z);
        return this.blocks[index];
    }

    public List<BlockContainer> getOpaque() {
        return this.opaque;
    }

    public List<BlockContainer> getWater() {
        return this.water;
    }
}
