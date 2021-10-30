package org.saar.core.light

import org.joml.Matrix4f
import org.joml.Vector3f
import org.saar.core.camera.ICamera
import org.saar.lwjgl.opengl.shaders.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3Uniform
import org.saar.maths.utils.Vector4

class ViewSpaceDirectionalLightUniform(name: String, var value: IDirectionalLight) : UniformContainer {

    var camera: ICamera? = null

    private val directionUniform = object : Vec3Uniform() {
        override val name = "$name.direction"

        override val value: Vector3f = Vector3f()
            get() {
                return if (this@ViewSpaceDirectionalLightUniform.camera != null) {
                    val vs = Vector4.of(this@ViewSpaceDirectionalLightUniform.value.direction, 0f)
                        .mul(this@ViewSpaceDirectionalLightUniform.camera!!.viewMatrix.invert(Matrix4f()).transpose())
                    field.set(vs.x(), vs.y(), vs.z())
                } else {
                    field.set(this@ViewSpaceDirectionalLightUniform.value.direction)
                }.normalize()
            }

    }

    private val colourUniform = object : Vec3Uniform() {
        override val name = "$name.colour"

        override val value get() = this@ViewSpaceDirectionalLightUniform.value.colour
    }

    override val subUniforms = listOf(this.directionUniform, this.colourUniform)
}