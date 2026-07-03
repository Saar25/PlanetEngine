package org.saar.core.light

import org.joml.Vector3fc

interface IDirectionalLight {
    val direction: Vector3fc
    val color: Vector3fc
}