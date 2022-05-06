package org.saar.minecraft

import org.saar.core.renderer.RenderingPath
import org.saar.core.renderer.renderpass.RenderPassBuffers

interface MinecraftRendering {

    fun buildRenderingPath(): RenderingPath<out RenderPassBuffers>

    fun update()

}