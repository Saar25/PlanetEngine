package org.saar.core.renderer.renderpass.shadow

import org.saar.core.renderer.renderpass.RenderPassRenderingBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class ShadowsRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    val normal: ReadOnlyTexture,
    val specular: ReadOnlyTexture,

    override val depth: ReadOnlyTexture
) : RenderPassRenderingBuffers