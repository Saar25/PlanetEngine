package org.saar.minecraft

import org.joml.Vector3i
import org.joml.Vector3ic

class BlockContainer(x: Int, y: Int, z: Int, val block: Block) {
    val position: Vector3ic = Vector3i(x, y, z)

    val x: Int get() = this.position.x()

    val y: Int get() = this.position.y()

    val z: Int get() = this.position.z()
}
