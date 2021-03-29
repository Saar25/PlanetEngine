package org.saar.minecraft.chunk;

import org.joml.Vector3i;
import org.joml.Vector3ic;

public class ChunkBounds {

    private final Vector3i min = new Vector3i();
    private final Vector3i max = new Vector3i();

    public void addBlock(Vector3ic position) {
        this.min.min(position);
        this.max.max(position);
    }

    public void addBlock(int x, int y, int z) {
        addBlock(new Vector3i(x, y, z));
    }

    public Vector3ic getMin() {
        return this.min;
    }

    public Vector3ic getMax() {
        return this.max;
    }
}
