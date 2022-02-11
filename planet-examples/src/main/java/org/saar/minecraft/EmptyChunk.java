package org.saar.minecraft;

public class EmptyChunk implements IChunk {

    private static final EmptyChunk INSTANCE = new EmptyChunk();

    private EmptyChunk() {

    }

    public static EmptyChunk getInstance() {
        return EmptyChunk.INSTANCE;
    }

    @Override
    public int getHeight(int x, int z) {
        return 0;
    }

    @Override
    public int getLight(int x, int y, int z) {
        return 0;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return Blocks.AIR;
    }

    @Override
    public void setBlock(int x, int y, int z, Block block) {
    }

    @Override
    public void updateMesh() {
    }
}
