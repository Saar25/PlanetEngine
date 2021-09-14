package org.saar.core.light

import org.joml.Vector3f
import org.saar.maths.utils.Vector3

class DirectionalLight : IDirectionalLight {
    override val direction: Vector3f = Vector3.create()
    override val colour: Vector3f = Vector3.create()
}