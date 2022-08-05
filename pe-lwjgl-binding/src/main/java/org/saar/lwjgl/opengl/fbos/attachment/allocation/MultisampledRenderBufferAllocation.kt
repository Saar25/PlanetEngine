package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

class MultisampledRenderBufferAllocation(
    private val iFormat: InternalFormat,
    private val samples: Int,
) : RenderBufferAllocationStrategy {

    override fun allocate(renderBuffer: RenderBuffer, width: Int, height: Int) {
        renderBuffer.loadStorageMultisample(width, height, this.iFormat, this.samples)
    }
}