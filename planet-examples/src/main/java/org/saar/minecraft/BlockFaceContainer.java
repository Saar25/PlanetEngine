package org.saar.minecraft;

import org.joml.Vector3i;
import org.joml.Vector3ic;

public class BlockFaceContainer {

    private final Vector3ic position;
    private final Block block;
    private final int direction;
    private final int shadow;
    private final boolean[] ambientOcclusion;

    public BlockFaceContainer(int x, int y, int z, Block block, int direction, int shadow, boolean[] ambientOcclusion) {
        this.position = new Vector3i(x, y, z);
        this.block = block;
        this.direction = direction;
        this.shadow = shadow;
        this.ambientOcclusion = ambientOcclusion;
    }

    public int getX() {
        return getPosition().x();
    }

    public int getY() {
        return getPosition().y();
    }

    public int getZ() {
        return getPosition().z();
    }

    public Vector3ic getPosition() {
        return this.position;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getDirection() {
        return this.direction;
    }

    public int getShadow() {
        return this.shadow;
    }

    public boolean[] getAmbientOcclusion() {
        return this.ambientOcclusion;
    }
}
