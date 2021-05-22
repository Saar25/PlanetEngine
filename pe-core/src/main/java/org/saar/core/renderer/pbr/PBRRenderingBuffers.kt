package org.saar.core.renderer.pbr

import org.saar.core.renderer.renderpass.RenderPassRenderingBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class PBRRenderingBuffers(
    override val albedo: ReadOnlyTexture,
    val normal: ReadOnlyTexture,
    val reflectivity: ReadOnlyTexture,

    override val depth: ReadOnlyTexture
) : RenderPassRenderingBuffers