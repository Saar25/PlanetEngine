package org.saar.maths.utils

import org.joml.Vector2f
import org.joml.Vector2fc

object Vector2 {

    @JvmStatic
    fun create() = Vector2f()

    @JvmStatic
    fun of(v: Vector2fc) = Vector2f(v.x(), v.y())

    @JvmStatic
    fun of(x: Float, y: Float) = Vector2f(x, y)

}
