package org.saar.core.light

import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.core.camera.ICamera
import org.saar.lwjgl.opengl.shaders.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3Uniform
import org.saar.maths.utils.Vector4

class ViewSpacePointLightUniform(name: String, var value: IPointLight) : UniformContainer {

    var camera: ICamera? = null

    private val positionUniform = object : Vec3Uniform() {
        override val name = "$name.position"

        override val value: Vector3fc
            get() {
                val vs = Vector4.of(this@ViewSpacePointLightUniform.value.position, 1f)
                    .mul(this@ViewSpacePointLightUniform.camera!!.viewMatrix)
                return Vector3f(vs.x(), vs.y(), vs.z()).div(vs.w)
            }
    }

    private val attenuationUniform = object : Vec3Uniform() {
        override val name = "$name.attenuation"

        override val value get() = this@ViewSpacePointLightUniform.value.attenuation.vector3f
    }

    private val colourUniform = object : Vec3Uniform() {
        override val name = "$name.colour"

        override val value get() = this@ViewSpacePointLightUniform.value.colour
    }

    override val subUniforms = listOf(this.positionUniform, this.attenuationUniform, this.colourUniform)
}