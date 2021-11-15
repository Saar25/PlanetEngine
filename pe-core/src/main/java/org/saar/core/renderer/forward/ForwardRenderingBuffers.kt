package org.saar.core.renderer.forward

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.renderer.renderpass.DepthBuffer
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

interface ForwardRenderingBuffers : PostProcessingBuffers, DepthBuffer {
    override val albedo: ReadOnlyTexture2D
    override val depth: ReadOnlyTexture2D
}