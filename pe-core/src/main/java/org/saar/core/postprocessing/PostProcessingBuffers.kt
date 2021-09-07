package org.saar.core.postprocessing

import org.saar.core.renderer.renderpass.AlbedoBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class PostProcessingBuffers(
    override val albedo: ReadOnlyTexture
) : RenderPassBuffers,
    AlbedoBuffer