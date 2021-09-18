package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.AlbedoBuffer
import org.saar.core.renderer.renderpass.DepthBuffer
import org.saar.core.renderer.renderpass.NormalSpecularBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture

data class DeferredRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    override val normalSpecular: ReadOnlyTexture,
    override val depth: ReadOnlyTexture
) : RenderPassBuffers,
    AlbedoBuffer,
    NormalSpecularBuffer,
    DepthBuffer