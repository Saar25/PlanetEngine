package org.saar.core.light

import org.joml.Vector3fc

interface IPointLight {
    val position: Vector3fc
    val attenuation: Attenuation
    val colour: Vector3fc
}