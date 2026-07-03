package org.saar.core.fog

import org.joml.Vector3fc

data class Fog(
    override val color: Vector3fc,
    override val start: Float,
    override val end: Float
) : IFog