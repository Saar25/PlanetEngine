package org.saar.core.common.r3d

import org.joml.Vector3fc
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

object R3D {

    @JvmStatic
    fun node(transform: Transform): Node3D {
        return Node3D { transform }
    }

    @JvmStatic
    fun node(): Node3D {
        return node(SimpleTransform())
    }

    @JvmStatic
    fun vertex(position: Vector3fc, normal: Vector3fc, colour: Vector3fc): Vertex3D {
        return object : Vertex3D {
            override fun getPosition3f(): Vector3fc = position

            override fun getNormal3f(): Vector3fc = normal

            override fun getColour3f(): Vector3fc = colour
        }
    }

}