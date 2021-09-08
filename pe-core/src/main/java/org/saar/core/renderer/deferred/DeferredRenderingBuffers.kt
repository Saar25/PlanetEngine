package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class DeferredRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    override val normal: ReadOnlyTexture,
    override val specular: ReadOnlyTexture,
    override val depth: ReadOnlyTexture
) : RenderPassBuffers,
    AlbedoBuffer,
    NormalBuffer,
    SpecularBuffer,
    DepthBuffer