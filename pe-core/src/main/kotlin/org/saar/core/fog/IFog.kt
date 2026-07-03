package org.saar.core.fog

import org.joml.Vector3fc

interface IFog {

    val color: Vector3fc

    val start: Float

    val end: Float

}