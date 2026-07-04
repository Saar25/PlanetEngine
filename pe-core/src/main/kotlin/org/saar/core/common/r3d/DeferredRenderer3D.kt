package org.saar.core.common.r3d

import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.shaders.*
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.maths.utils.Matrix4
import org.saar.rhi.opengl.resterization.toOpengl
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.RasterizationState

val DeferredRenderer3D = renderer<DeferredRenderContext, Model3D> {
    val rasterizationState = RasterizationState(
        cullMode = CullMode.BACK,
    ).toOpengl()

    shadersLink {
        vertexAttributes = arrayOf("in_position", "in_color", "in_transformation")

        val clipPlaneUniform = uniformVec4("u_clipPlane")
        val specularUniform = uniformFloat("u_specular")
        val modelMatrixUniform = uniformMat4("u_modelMatrix")
        val mvpMatrixUniform = uniformMat4("u_mvpMatrix")
        val normalMatrixUniform = uniformMat4("u_normalMatrix")

        onRender { context, models ->
            BlendTest.disable()
            DepthTest.enable()
            rasterizationState.set()

            normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()

            val v = context.camera.viewMatrix
            val p = context.camera.projection.matrix
            val vp = p.mul(v, Matrix4.create())

            models.forEach { model ->
                val m = model.transform.transformationMatrix

                specularUniform.value = model.specular
                modelMatrixUniform.value.set(m)
                mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

                uniformsLoader.load()

                model.mesh.draw()
            }
        }

        shadersProgram {
            vertex {
                version = GlslVersion.V400
                source { "/shaders/r3d/r3d.vertex.glsl" }
            }
            fragment {
                version = GlslVersion.V400
                source { "/shaders/r3d/r3d.dfragment.glsl" }
            }
        }
    }
}
