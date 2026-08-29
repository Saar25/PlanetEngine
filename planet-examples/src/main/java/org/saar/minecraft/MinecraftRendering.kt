package org.saar.minecraft

import org.saar.core.renderer.RenderGraph

interface MinecraftRendering {

    fun buildRenderGraph(): RenderGraph

    fun update()

}
