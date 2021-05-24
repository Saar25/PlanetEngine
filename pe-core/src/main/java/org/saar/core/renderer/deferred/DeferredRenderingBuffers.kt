package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class DeferredRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    val normal: ReadOnlyTexture,
    val specular: ReadOnlyTexture,
    val depth: ReadOnlyTexture
) : RenderPassBuffers