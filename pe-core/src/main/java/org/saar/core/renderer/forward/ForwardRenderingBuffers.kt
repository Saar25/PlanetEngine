package org.saar.core.renderer.forward

import org.saar.core.renderer.renderpass.AlbedoBuffer
import org.saar.core.renderer.renderpass.DepthBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture

data class ForwardRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    override val depth: ReadOnlyTexture
) : RenderPassBuffers,
    AlbedoBuffer,
    DepthBuffer