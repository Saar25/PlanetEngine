package org.saar.core.renderer.deferred.light

import org.saar.core.light.DirectionalLight
import org.saar.core.light.PointLight
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class LightRenderPassInstance(val image: ReadOnlyTexture,
                                   val pointLights: Array<PointLight>,
                                   val directionalLights: Array<DirectionalLight>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LightRenderPassInstance

        if (image != other.image) return false
        if (!pointLights.contentEquals(other.pointLights)) return false
        if (!directionalLights.contentEquals(other.directionalLights)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image.hashCode()
        result = 31 * result + pointLights.contentHashCode()
        result = 31 * result + directionalLights.contentHashCode()
        return result
    }
}