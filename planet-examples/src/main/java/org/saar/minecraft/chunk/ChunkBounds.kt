package org.saar.minecraft.chunk

import org.joml.Vector3i
import org.joml.Vector3ic

class ChunkBounds(
    val min: Vector3i = Vector3i(),
    val max: Vector3i = Vector3i()
) {

    fun addBlock(position: Vector3ic) {
        this.min.min(position)
        this.max.max(position)
    }

    fun addBlock(x: Int, y: Int, z: Int) {
        addBlock(Vector3i(x, y, z))
    }
}
