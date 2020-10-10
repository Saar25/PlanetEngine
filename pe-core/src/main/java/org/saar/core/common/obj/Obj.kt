package org.saar.core.common.obj

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

object Obj {

    @JvmStatic
    fun node(transform: Transform, texture: ReadOnlyTexture): ObjNode {
        return object : ObjNode {
            override fun getTransform(): Transform = transform

            override fun getTexture(): ReadOnlyTexture = texture
        }
    }

    @JvmStatic
    fun node(texture: ReadOnlyTexture): ObjNode {
        return node(SimpleTransform(), texture)
    }

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc): ObjVertex {
        return object : ObjVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getUvCoord2f(): Vector2fc = uvCoord

            override fun getNormal3f(): Vector3fc = normal
        }
    }

}