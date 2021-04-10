package org.saar.core.renderer.deferred

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class DeferredRenderingPipeline(private vararg val renderPasses: RenderPass) {

    fun render(colourTexture: ReadOnlyTexture, buffers: DeferredRenderingBuffers) {
        var currentBuffers = buffers
        for (renderPass in this.renderPasses) {
            renderPass.render(currentBuffers)

            currentBuffers = DeferredRenderingBuffers(colourTexture,
                currentBuffers.normal, currentBuffers.depth)
        }
    }

    fun delete() {
        this.renderPasses.forEach { it.delete() }
    }
}