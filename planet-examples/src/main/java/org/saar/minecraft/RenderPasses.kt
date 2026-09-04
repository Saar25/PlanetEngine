package org.saar.minecraft

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.screen.Screen
import org.saar.gui.UIDisplay
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.minecraft.chunk.ChunkRenderer
import org.saar.minecraft.chunk.WaterRenderer

class ForwardNodeScreenPass(
    private val screen: Screen,
    private val camera: ICamera,
    private val renderNode: ForwardRenderNode,
    private val clear: Boolean,
) : RenderPass {

    override fun render(context: RenderContext) {
        this.screen.setAsDraw()
        if (this.clear) {
            GlUtils.clear(GlBuffer.COLOR, GlBuffer.DEPTH)
        }
        this.renderNode.renderForward(ForwardRenderContext(context, this.camera))
    }

    override fun delete() = this.renderNode.delete()
}

class WorldForwardNode(private val world: World) : ForwardRenderNode {

    override fun renderForward(context: ForwardRenderContext) {
        ChunkRenderer.render(context.camera, this.world)
        WaterRenderer.render(context.camera, this.world)
    }
}

class UIRenderPass(private val uiDisplay: UIDisplay) : RenderPass {

    override fun render(context: RenderContext) {
        this.uiDisplay.render(context)
    }

    override fun delete() = this.uiDisplay.delete()
}
