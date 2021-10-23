package org.saar.core.renderer.deferred

import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.NormalSpecularBuffer
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

interface DeferredRenderingBuffers : ForwardRenderingBuffers, NormalSpecularBuffer {
    override val albedo: ReadOnlyTexture2D
    override val normalSpecular: ReadOnlyTexture2D
    override val depth: ReadOnlyTexture2D
}