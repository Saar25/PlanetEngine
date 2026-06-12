package org.saar.minecraft

import org.saar.core.renderer.RenderPipeline

interface MinecraftRendering {

    fun buildRenderPipeline(): RenderPipeline

    fun update()

}