package org.saar.core.renderer.deferred

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

data class DeferredRenderingBuffers(
        val albedo: ReadOnlyTexture,
        val normal: ReadOnlyTexture,
        val depth: ReadOnlyTexture
)