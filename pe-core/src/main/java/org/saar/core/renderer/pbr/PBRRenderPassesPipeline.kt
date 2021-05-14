package org.saar.core.renderer.pbr

import org.saar.core.camera.ICamera
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class PBRRenderPassesPipeline(private vararg val renderPasses: PBRRenderPass) {

    private val prototype = PBRRenderPassesScreenPrototype()

    private val screen: OffScreen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    private val colourTexture: Texture
        get() = this.prototype.colourTexture

    fun process(camera: ICamera, buffers: PBRRenderingBuffers): PBRRenderingOutput {
        this.screen.resizeToMainScreen()

        this.screen.setAsDraw()
        GlUtils.clear(GlBuffer.COLOUR)

        val context = RenderPassContext(camera)

        var currentBuffers = buffers
        for (renderPass in this.renderPasses) {
            renderPass.renderPBR(context, currentBuffers)

            currentBuffers = PBRRenderingBuffers(this.colourTexture,
                currentBuffers.normal, currentBuffers.reflectivity, currentBuffers.depth)
        }

        return PBRRenderingOutput(this.screen, this.colourTexture, buffers.depth)
    }

    fun delete() {
        this.screen.delete()
        this.renderPasses.forEach { it.delete() }
    }
}