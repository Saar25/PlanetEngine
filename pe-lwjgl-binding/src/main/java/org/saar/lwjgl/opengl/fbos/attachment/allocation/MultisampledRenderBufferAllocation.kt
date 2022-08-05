package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

class MultisampledRenderBufferAllocation(
    private val width: Int,
    private val height: Int,
    private val iFormat: InternalFormat,
    private val samples: Int,
) : RenderBufferAllocationStrategy {

    override fun allocate(renderBuffer: RenderBuffer) {
        renderBuffer.loadStorageMultisample(this.width, this.height, this.iFormat, this.samples)
    }
}