package org.saar.maths.objects

import org.joml.Vector2f
import org.saar.maths.utils.Vector2.of

class Polygon {

    val vertices = mutableListOf<Vector2f>()

    fun addVertex(x: Float, y: Float) {
        this.vertices.add(of(x, y))
    }
}
