package org.saar.core.renderer.deferred.ssao

import org.saar.core.renderer.renderpass.RenderPassRenderingBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class SsaoRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    val normal: ReadOnlyTexture,

    override val depth: ReadOnlyTexture
) : RenderPassRenderingBuffers