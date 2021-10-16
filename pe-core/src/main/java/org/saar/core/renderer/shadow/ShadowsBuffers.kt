package org.saar.core.renderer.shadow

import org.saar.core.renderer.renderpass.DepthBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

data class ShadowsBuffers(
    override val depth: ReadOnlyTexture2D,
) : RenderPassBuffers,
    DepthBuffer