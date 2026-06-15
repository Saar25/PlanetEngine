package org.saar.core.common.r3d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.init
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.maths.utils.Matrix4

object DeferredRenderer3D {

    private val shadersLink = DeferredShadersLink3D

    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    fun render(context: RenderContext, vararg models: Model3D) = render(context, models.asIterable())

    fun render(context: RenderContext, models: Iterable<Model3D>) {
        this.shadersLink.shadersProgram.bind()

        ProvokingVertex.setFirst()
        BlendTest.disable()
        DepthTest.enable()
        CullFace.enable()

        this.shadersLink.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val vp = p.mul(v, Matrix4.create())

        models.forEach { model ->
            val m = model.transform.transformationMatrix

            this.shadersLink.specularUniform.value = model.specular
            this.shadersLink.modelMatrixUniform.value.set(m)
            this.shadersLink.mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

            this.uniformsLoader.load()

            model.mesh.draw()
        }
    }
}