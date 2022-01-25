package org.saar.minecraft;

public interface IChunk {

    int getHeight(int x, int z);

    Block getBlock(int x, int y, int z);

    void setBlock(int x, int y, int z, Block block);

    void updateMesh(World world);

}