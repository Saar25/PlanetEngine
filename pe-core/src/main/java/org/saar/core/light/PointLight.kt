package org.saar.core.light

import org.joml.Vector3f
import org.saar.maths.utils.Vector3

class PointLight : IPointLight {
    override val position: Vector3f = Vector3.create()
    override var attenuation: Attenuation = Attenuation.DISTANCE_7
    override val colour: Vector3f = Vector3.create()
}