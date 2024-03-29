package org.saar.core.renderer.shadow

import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.light.IDirectionalLight
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderingOutput
import org.saar.core.renderer.RenderingPath
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.constants.Face
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class ShadowsRenderingPath(
    quality: ShadowsQuality,
    projection: OrthographicProjection,
    light: IDirectionalLight,
    private val renderNode: ShadowsRenderNode,
) : RenderingPath<ShadowsBuffers> {

    val camera: ShadowsCamera = ShadowsCamera(projection).apply {
        transform.rotation.lookAlong(light.direction)
        transform.position.set(0f, 0f, 0f)
    }

    private val prototype = ShadowsScreenPrototype()

    private val screen = Screens.fromPrototype(this.prototype,
        Fbo.create(quality.imageSize, quality.imageSize),
        SimpleAllocationStrategy())

    override fun render(): RenderingOutput<ShadowsBuffers> {
        this.screen.setAsDraw()

        GlUtils.clear(GlBuffer.DEPTH)

        val context = RenderContext(this.camera)

        CullFace.set(true, Face.FRONT)
        this.renderNode.renderShadows(context)

        return RenderingOutput(this.screen, this.prototype.buffers)
    }

    override fun delete() {
        this.renderNode.delete()
        this.screen.delete()
    }
}