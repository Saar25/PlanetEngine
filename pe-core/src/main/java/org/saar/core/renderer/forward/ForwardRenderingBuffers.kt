package org.saar.core.renderer.forward

import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class ForwardRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    val depth: ReadOnlyTexture
) : RenderPassBuffers