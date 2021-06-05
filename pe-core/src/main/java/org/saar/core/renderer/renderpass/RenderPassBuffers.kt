package org.saar.core.renderer.renderpass

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

interface RenderPassBuffers {
    val albedo: ReadOnlyTexture
}