package org.saar.lwjgl.opengl.shader.uniforms

import org.joml.Vector3i
import org.joml.Vector3ic
import org.saar.maths.JomlDelegates

class Vec3iUniformValue(
    override val name: String,
    initialValue: Vector3ic = Vector3i(0, 0, 0)
) : Vec3iUniform() {
    override var value: Vector3i by JomlDelegates.CachedVector3i(Vector3i(initialValue))
}