package org.saar.minecraft;

import org.joml.Vector3i;
import org.joml.Vector3ic;

public class BlockFaceContainer {

    private final Vector3ic position;
    private final Block block;
    private final int direction;

    public BlockFaceContainer(int x, int y, int z, Block block, int direction) {
        this.position = new Vector3i(x, y, z);
        this.block = block;
        this.direction = direction;
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
}
