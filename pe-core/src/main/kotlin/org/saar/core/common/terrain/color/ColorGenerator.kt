package org.saar.core.common.terrain.color

import org.joml.Vector3fc

fun interface ColorGenerator {
    fun generateColor(position: Vector3fc, normal: Vector3fc): Vector3fc
}
