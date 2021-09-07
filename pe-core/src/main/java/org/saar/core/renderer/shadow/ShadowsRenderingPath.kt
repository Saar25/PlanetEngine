package org.saar.core.renderer.shadow

import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.light.IDirectionalLight
import org.saar.core.renderer.RenderContextBase
import org.saar.core.renderer.RenderingPath
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.textures.TextureTarget
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting
import org.saar.lwjgl.opengl.textures.settings.TextureSWrapSetting
import org.saar.lwjgl.opengl.textures.settings.TextureTWrapSetting
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

    override fun render(): ShadowRenderingOutput {
        this.screen.setAsDraw()

        GlUtils.clear(GlBuffer.DEPTH)

        val context = RenderContextBase(this.camera)

        context.hints.cullFace = GlCullFace.FRONT
        this.renderNode.renderShadows(context)

        return ShadowRenderingOutput(this.screen, ShadowsBuffers(this.prototype.depthTexture))
    }

    override fun delete() {
        this.renderNode.delete()
        this.screen.delete()
    }

    init {
        this.prototype.depthTexture.setSettings(TextureTarget.TEXTURE_2D,
            TextureMinFilterSetting(MinFilterParameter.LINEAR),
            TextureMagFilterSetting(MagFilterParameter.LINEAR),
            TextureSWrapSetting(WrapParameter.CLAMP_TO_EDGE),
            TextureTWrapSetting(WrapParameter.CLAMP_TO_EDGE))
    }
}