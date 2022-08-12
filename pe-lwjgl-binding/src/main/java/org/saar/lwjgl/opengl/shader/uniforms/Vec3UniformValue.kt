package org.saar.lwjgl.opengl.shader.uniforms

import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.maths.JomlDelegates

class Vec3UniformValue(
    override val name: String,
    initialValue: Vector3fc = Vector3f(0f, 0f, 0f)
) : Vec3Uniform() {
    override var value: Vector3f by JomlDelegates.CachedVector3f(Vector3f(initialValue))
}