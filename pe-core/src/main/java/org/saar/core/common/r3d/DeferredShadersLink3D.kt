package org.saar.core.common.r3d

import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec4UniformValue

object DeferredShadersLink3D : ShadersLink {

    @UniformProperty
    val clipPlaneUniform = Vec4UniformValue("u_clipPlane")

    @UniformProperty
    val specularUniform = FloatUniformValue("u_specular")

    @UniformProperty
    val modelMatrixUniform = Mat4UniformValue("u_modelMatrix")

    @UniformProperty
    val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

    override val vertexAttributes = arrayOf("in_position", "in_colour", "in_transformation")

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/r3d/r3d.vertex.glsl")),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/r3d/r3d.dfragment.glsl"))
    )
}