package org.saar.minecraft

import org.joml.Vector3i

class BlockFaceContainer(
    x: Int,
    y: Int,
    z: Int,
    val block: Block,
    val direction: Int,
    val light: Int,
    val ambientOcclusion: BooleanArray
) {
    val position = Vector3i(x, y, z)

    val x: Int get() = this.position.x()

    val y: Int get() = this.position.y()

    val z: Int get() = this.position.z()
}
