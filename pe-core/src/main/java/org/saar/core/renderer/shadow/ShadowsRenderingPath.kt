package org.saar.core.renderer.shadow

import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.light.IDirectionalLight
import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.RenderingOutput
import org.saar.core.renderer.RenderingPath
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureSWrapParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureTWrapParameter
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.lwjgl.opengl.texture.values.WrapValue
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils

class ShadowsRenderingPath(
    quality: ShadowsQuality,
    projection: OrthographicProjection,
    light: IDirectionalLight,
    private val renderNode: ShadowsRenderNode) : RenderingPath<ShadowsBuffers> {

    val camera: ShadowsCamera = ShadowsCamera(projection).apply {
        transform.rotation.lookAlong(light.direction)
        transform.position.set(0f, 0f, 0f)
    }

    private val prototype = ShadowsScreenPrototype()

    private val screen = Screens.fromPrototype(this.prototype,
        Fbo.create(quality.imageSize, quality.imageSize))

    override fun render(): RenderingOutput<ShadowsBuffers> {
        this.screen.setAsDraw()

        GlUtils.clear(GlBuffer.DEPTH)

        val context = RenderContextBase(this.camera)

        context.hints.cullFace = GlCullFace.FRONT
        this.renderNode.renderShadows(context)

        return RenderingOutput(this.screen, ShadowsBuffers(this.prototype.depthTexture))
    }

    override fun delete() {
        this.renderNode.delete()
        this.screen.delete()
    }

    init {
        this.prototype.depthTexture.applyParameters(
            TextureMinFilterParameter(MinFilterValue.LINEAR),
            TextureMagFilterParameter(MagFilterValue.LINEAR),
            TextureSWrapParameter(WrapValue.CLAMP_TO_EDGE),
            TextureTWrapParameter(WrapValue.CLAMP_TO_EDGE))
    }
}