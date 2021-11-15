package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector4i
import org.joml.Vector4ic
import org.saar.maths.JomlDelegates

class Vec4iUniformValue(
    override val name: String,
    initialValue: Vector4ic = Vector4i(0, 0, 0, 0)
) : Vec4iUniform() {
    override var value: Vector4i by JomlDelegates.CachedVector4i(Vector4i(initialValue))
}