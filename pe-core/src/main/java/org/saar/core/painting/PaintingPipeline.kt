package org.saar.core.painting

import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.stencil.StencilTest

class PaintingPipeline(private vararg val painters: Painter) {

    fun process() = this.painters.forEach {
        StencilTest.disable()
        DepthTest.disable()
        BlendTest.disable()

        it.render()
    }

    fun delete() = this.painters.forEach { it.delete() }

}