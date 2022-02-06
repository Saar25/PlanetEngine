package org.saar.minecraft;

public interface IChunk {

    int getHeight(int x, int z);

    int getSolidHeight(int x, int z);

    int getLight(int x, int y, int z);

    Block getBlock(int x, int y, int z);

    void setBlock(int x, int y, int z, Block block);

    void updateMesh(World world);
}
