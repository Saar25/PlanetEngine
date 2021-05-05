package org.saar.core.renderer.deferred.light

import org.saar.core.renderer.renderpass.RenderPassRenderingBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

interface LightRenderingBuffers : RenderPassRenderingBuffers {
    override val albedo: ReadOnlyTexture
    override val depth: ReadOnlyTexture
    val normal: ReadOnlyTexture
}