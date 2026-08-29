package org.saar.minecraft.chunk

import org.joml.Vector3i
import org.joml.Vector3ic

object ChunkConstants {

    @JvmField
    val blockDirections = arrayOf<Vector3ic>(
        Vector3i(+1, 0, 0),
        Vector3i(-1, 0, 0),
        Vector3i(0, +1, 0),
        Vector3i(0, -1, 0),
        Vector3i(0, 0, +1),
        Vector3i(0, 0, -1),
    )

    @JvmField
    val ambientOcclusionOrders = arrayOf(
        intArrayOf(1, 3, 7, 0, 3, 5, 0, 2, 4, 1, 2, 6),
        intArrayOf(1, 2, 6, 0, 2, 4, 0, 3, 5, 1, 3, 7),
        intArrayOf(1, 3, 7, 0, 3, 5, 0, 2, 4, 1, 2, 6),
        intArrayOf(1, 2, 6, 0, 2, 4, 0, 3, 5, 1, 3, 7),
        intArrayOf(0, 3, 5, 0, 2, 4, 1, 2, 6, 1, 3, 7),
        intArrayOf(1, 3, 7, 1, 2, 6, 0, 2, 4, 0, 3, 5),
    )
}