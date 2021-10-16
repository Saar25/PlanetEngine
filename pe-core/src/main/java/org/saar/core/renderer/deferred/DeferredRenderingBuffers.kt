package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.AlbedoBuffer
import org.saar.core.renderer.renderpass.DepthBuffer
import org.saar.core.renderer.renderpass.NormalSpecularBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

data class DeferredRenderingBuffers(
    override val albedo: ReadOnlyTexture2D,
    override val normalSpecular: ReadOnlyTexture2D,
    override val depth: ReadOnlyTexture2D
) : RenderPassBuffers,
    AlbedoBuffer,
    NormalSpecularBuffer,
    DepthBuffer