package org.saar.core.postprocessing

import org.saar.core.camera.ICamera
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.stencil.*

class PostProcessingPipeline(private vararg val processors: PostProcessor) {

    private val stencilState = StencilState(StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF), StencilMask.UNCHANGED)

    fun process(buffers: PostProcessingBuffers) = process(null, buffers)

    fun process(camera: ICamera?, buffers: PostProcessingBuffers) {
        this.processors.forEach { processor ->
            StencilTest.apply(this.stencilState)
            DepthTest.disable()

            processor.process(PostProcessingContext(camera, buffers))
        }
    }

    fun delete() = this.processors.forEach { it.delete() }

}