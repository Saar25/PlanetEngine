package org.saar.core.renderer.forward

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.RenderingPath
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class ForwardRenderingPath(private val camera: ICamera, private val renderNode: ForwardRenderNode) : RenderingPath {

    private val prototype = ForwardScreenPrototype()

    private val screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    override fun render(): ForwardRenderingOutput {
        this.screen.resizeToMainScreen()
        this.screen.setAsDraw()

        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH)

        this.renderNode.renderForward(RenderContextBase(this.camera))

        return ForwardRenderingOutput(this.screen, this.prototype.asBuffers())
    }

    override fun delete() {
        this.renderNode.delete()
        this.screen.delete()
    }
}