package org.saar.core.common.normalmap

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform
import org.saar.maths.utils.Vector3

object NormalMapped {

    @JvmStatic
    fun node(transform: Transform, texture: ReadOnlyTexture, normalMap: ReadOnlyTexture): NormalMappedNode {
        return object : NormalMappedNode {
            override fun getTransform(): Transform = transform

            override fun getTexture(): ReadOnlyTexture = texture

            override fun getNormalMap(): ReadOnlyTexture = normalMap
        }
    }

    @JvmStatic
    fun node(texture: ReadOnlyTexture, normalMap: ReadOnlyTexture): NormalMappedNode {
        return node(SimpleTransform(), texture, normalMap)
    }

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc, tangent: Vector3fc, biTangent: Vector3fc): NormalMappedVertex {
        return object : NormalMappedVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getUvCoord2f(): Vector2fc = uvCoord

            override fun getNormal3f(): Vector3fc = normal

            override fun getTangent3f(): Vector3fc = tangent

            override fun getBiTangent3f(): Vector3fc = biTangent
        }
    }

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc, tangent: Vector3fc): NormalMappedVertex {
        val biTangent: Vector3fc = Vector3.cross(normal, tangent)
        return vertex(position, uvCoord, normal, tangent, biTangent)
    }
}