package org.saar.core.renderer.deferred.light

import org.saar.core.renderer.renderpass.RenderPassRenderingBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class LightRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    val normal: ReadOnlyTexture,

    override val depth: ReadOnlyTexture
) : RenderPassRenderingBuffers