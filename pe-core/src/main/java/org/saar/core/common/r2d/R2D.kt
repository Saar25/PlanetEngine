package org.saar.core.common.r2d

import org.joml.Vector2fc
import org.joml.Vector3fc

object R2D {

    @JvmStatic
    fun node(): Node2D {
        return object : Node2D {}
    }

    @JvmStatic
    fun vertex(position: Vector2fc, colour: Vector3fc): Vertex2D {
        return object : Vertex2D {
            override fun getPosition2f(): Vector2fc = position

            override fun getColour3f(): Vector3fc = colour
        }
    }

}