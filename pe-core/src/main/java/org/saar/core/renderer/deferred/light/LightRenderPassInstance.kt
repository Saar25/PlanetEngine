package org.saar.core.renderer.deferred.light

import org.saar.core.light.DirectionalLight
import org.saar.core.light.PointLight
import org.saar.core.renderer.deferred.DeferredRenderingBuffers

data class LightRenderPassInstance(val buffers: DeferredRenderingBuffers,
                                   val pointLights: Array<PointLight>,
                                   val directionalLights: Array<DirectionalLight>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LightRenderPassInstance

        if (buffers != other.buffers) return false
        if (!pointLights.contentEquals(other.pointLights)) return false
        if (!directionalLights.contentEquals(other.directionalLights)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = buffers.hashCode()
        result = 31 * result + pointLights.contentHashCode()
        result = 31 * result + directionalLights.contentHashCode()
        return result
    }
}