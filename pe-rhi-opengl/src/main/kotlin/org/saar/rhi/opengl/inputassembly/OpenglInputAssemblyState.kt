package org.saar.rhi.opengl.inputassembly

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL31
import org.lwjgl.opengl.GL32
import org.lwjgl.opengl.GL40
import org.saar.rhi.inputassembly.InputAssemblyState
import org.saar.rhi.inputassembly.PrimitiveTopology

fun InputAssemblyState.toOpengl() = OpenglInputAssemblyState(this)

class OpenglInputAssemblyState(inputAssemblyState: InputAssemblyState) {
    private val topology = inputAssemblyState.topology ?: PrimitiveTopology.TRIANGLE_LIST
    private val primitiveRestartEnable = inputAssemblyState.primitiveRestartEnable ?: false

    fun set() {
        if (this.primitiveRestartEnable) {
            GL31.glEnable(GL31.GL_PRIMITIVE_RESTART)
            GL31.glPrimitiveRestartIndex(0xFFFF)
        } else {
            GL31.glDisable(GL31.GL_PRIMITIVE_RESTART)
        }
    }

    // TODO: use this when making draw calls (glDrawArrays)
    val glTopology = this.topology.glValue
}

val PrimitiveTopology.glValue
    get() = when (this) {
        PrimitiveTopology.POINT_LIST -> GL11.GL_POINTS
        PrimitiveTopology.LINE_LIST -> GL11.GL_LINES
        PrimitiveTopology.LINE_STRIP -> GL11.GL_LINE_STRIP
        PrimitiveTopology.TRIANGLE_LIST -> GL11.GL_TRIANGLES
        PrimitiveTopology.TRIANGLE_STRIP -> GL11.GL_TRIANGLE_STRIP
        PrimitiveTopology.TRIANGLE_FAN -> GL11.GL_TRIANGLE_FAN
        PrimitiveTopology.LINE_LIST_ADJACENCY -> GL32.GL_LINES_ADJACENCY
        PrimitiveTopology.LINE_STRIP_ADJACENCY -> GL32.GL_LINE_STRIP_ADJACENCY
        PrimitiveTopology.TRIANGLE_LIST_ADJACENCY -> GL32.GL_TRIANGLES_ADJACENCY
        PrimitiveTopology.TRIANGLE_STRIP_ADJACENCY -> GL32.GL_TRIANGLE_STRIP_ADJACENCY
        PrimitiveTopology.PATCH_LIST -> GL40.GL_PATCHES
    }
