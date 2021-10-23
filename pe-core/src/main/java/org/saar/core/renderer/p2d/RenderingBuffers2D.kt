package org.saar.core.renderer.p2d

import org.saar.core.renderer.renderpass.AlbedoBuffer
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

interface RenderingBuffers2D : RenderPassBuffers, AlbedoBuffer {
    override val albedo: ReadOnlyTexture2D
}