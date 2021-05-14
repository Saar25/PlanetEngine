package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class DeferredRenderPassesPipeline(private vararg val renderPasses: DeferredRenderPass) {

    private val prototype = DeferredRenderPassesScreenPrototype()

    private val screen: OffScreen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    private val colourTexture: Texture
        get() = this.prototype.colourTexture

    fun process(camera: ICamera, buffers: DeferredRenderingBuffers): DeferredRenderingOutput {
        this.screen.resizeToMainScreen()

        this.screen.setAsDraw()
        GlUtils.clear(GlBuffer.COLOUR)

        val context = RenderPassContext(camera)

        var currentBuffers = buffers
        for (renderPass in this.renderPasses) {
            renderPass.render(context, currentBuffers)

            currentBuffers = DeferredRenderingBuffers(this.colourTexture,
                currentBuffers.normal, currentBuffers.depth)
        }

        return DeferredRenderingOutput(this.screen, this.colourTexture, buffers.depth)
    }

    fun delete() {
        this.screen.delete()
        this.renderPasses.forEach { it.delete() }
    }
}