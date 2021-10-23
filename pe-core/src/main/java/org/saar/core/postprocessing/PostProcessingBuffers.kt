package org.saar.core.postprocessing

import org.saar.core.renderer.renderpass.AlbedoBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

interface PostProcessingBuffers : RenderPassBuffers, AlbedoBuffer {
    override val albedo: ReadOnlyTexture2D
}