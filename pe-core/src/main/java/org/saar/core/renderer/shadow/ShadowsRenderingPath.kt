package org.saar.core.renderer.shadow

import org.jproperty.InvalidationListener
import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.light.IDirectionalLight
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderingOutput
import org.saar.core.renderer.RenderingPath
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.constants.Face
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.FboBlitFilter
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class ShadowsRenderingPath @JvmOverloads constructor(
    quality: ShadowsQuality,
    projection: OrthographicProjection,
    light: IDirectionalLight,
    private val dynamicNode: ShadowsRenderNode,
    private val staticNode: ShadowsRenderNode? = null,
) : RenderingPath<ShadowsBuffers> {

    val camera: ShadowsCamera = ShadowsCamera(projection, light)

    private val prototype = ShadowsScreenPrototype()

    private val screen = Screens.fromPrototype(this.prototype,
        Fbo.create(quality.imageSize, quality.imageSize),
        SimpleAllocationStrategy())

    private val staticPrototype = ShadowsScreenPrototype()

    private val staticScreen = Screens.fromPrototype(this.staticPrototype,
        Fbo.create(quality.imageSize, quality.imageSize),
        SimpleAllocationStrategy())

    private var validStaticMap: Boolean = false

    init {
        this.camera.transform.transformation.addListener(InvalidationListener {
            this.validStaticMap = false
        })
    }

    override fun render(): RenderingOutput<ShadowsBuffers> {
        validateStaticMap()

        this.screen.setAsDraw()
        CullFace.set(true, Face.BACK)

        val context = RenderContext(this.camera)
        this.dynamicNode.renderShadows(context)

        return RenderingOutput(this.screen, this.prototype.buffers)
    }

    private fun validateStaticMap() {
        if (!this.validStaticMap && this.staticNode != null) {
            val context = RenderContext(this.camera)
            this.staticScreen.setAsDraw()
            GlUtils.clear(GlBuffer.DEPTH)
            this.staticNode.renderShadows(context)

            this.validStaticMap = true
        }

        if (this.validStaticMap) {
            this.staticScreen.copyTo(this.screen,
                FboBlitFilter.NEAREST, GlBuffer.DEPTH)
        }
    }

    override fun delete() {
        this.dynamicNode.delete()
        this.staticNode?.delete()
        this.screen.delete()
    }
}