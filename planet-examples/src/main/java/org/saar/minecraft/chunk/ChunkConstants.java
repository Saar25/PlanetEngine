package org.saar.minecraft.chunk;

import org.joml.Vector3i;
import org.joml.Vector3ic;

public final class ChunkConstants {

    public static final Vector3ic[] blockDirections = new Vector3i[]{
            new Vector3i(+1, 0, 0),
            new Vector3i(-1, 0, 0),
            new Vector3i(0, +1, 0),
            new Vector3i(0, -1, 0),
            new Vector3i(0, 0, +1),
            new Vector3i(0, 0, -1),
    };

    public static final int[][] ambientOcclusionOrders = new int[][]{
            {1, 3, 7, 0, 3, 5, 0, 2, 4, 1, 2, 6},
            {1, 2, 6, 0, 2, 4, 0, 3, 5, 1, 3, 7},
            {1, 3, 7, 0, 3, 5, 0, 2, 4, 1, 2, 6},
            {1, 3, 7, 0, 3, 5, 0, 2, 4, 1, 2, 6},
            {0, 3, 5, 0, 2, 4, 1, 2, 6, 1, 3, 7},
            {1, 3, 7, 1, 2, 6, 0, 2, 4, 0, 3, 5},
    };

    private ChunkConstants() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }
}
