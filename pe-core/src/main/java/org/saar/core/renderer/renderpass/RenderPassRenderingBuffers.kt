package org.saar.core.renderer.renderpass

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

interface RenderPassRenderingBuffers {
    val albedo: ReadOnlyTexture
    val depth: ReadOnlyTexture
}