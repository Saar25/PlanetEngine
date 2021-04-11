package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class RenderPassesPipeline(private vararg val renderPasses: RenderPass) {

    private val prototype = RenderPassesScreenPrototype()

    private val screen: OffScreen = Screens.fromPrototype(this.prototype, Fbo.create(1200, 700))

    private val colourTexture: Texture
        get() = this.prototype.colourTexture

    fun process(camera: ICamera, buffers: DeferredRenderingBuffers): DeferredRenderingOutput {
        this.screen.setAsDraw()
        GlUtils.clear(GlBuffer.COLOUR)

        var currentBuffers = buffers
        for (renderPass in this.renderPasses) {
            renderPass.render(RenderPassContext(camera, currentBuffers))

            currentBuffers = DeferredRenderingBuffers(this.colourTexture,
                currentBuffers.normal, currentBuffers.depth)
        }

        return DeferredRenderingOutput(this.screen, this.colourTexture)
    }

    fun delete() {
        this.screen.delete()
        this.renderPasses.forEach { it.delete() }
    }
}