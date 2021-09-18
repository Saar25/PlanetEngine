package org.saar.core.postprocessing

import org.saar.core.renderer.renderpass.AlbedoBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class PostProcessingBuffers(
    override val albedo: ReadOnlyTexture2D
) : RenderPassBuffers,
    AlbedoBuffer