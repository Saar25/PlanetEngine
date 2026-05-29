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

    @JvmStatic
    fun of(d: Float) = of(d, d)

    @JvmStatic
    fun right() = of(1f, 0f)

    @JvmStatic
    fun left() = of(-1f, 0f)
}
