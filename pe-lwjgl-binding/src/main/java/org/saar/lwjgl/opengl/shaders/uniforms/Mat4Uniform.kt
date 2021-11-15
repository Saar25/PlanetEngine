package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Matrix4fc
import org.lwjgl.opengl.GL20
import org.lwjgl.system.MemoryStack

abstract class Mat4Uniform : Uniform {

    final override fun load(location: Int) {
        val value: Matrix4fc = this.value
        MemoryStack.stackPush().use { stack ->
            val buffer = value.get(stack.callocFloat(16))
            GL20.glUniformMatrix4fv(location, this.transpose, buffer)
        }
    }

    abstract val value: Matrix4fc
    abstract val transpose: Boolean
}