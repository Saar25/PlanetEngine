package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

class SimpleRenderBufferAllocation(
    private val iFormat: InternalFormat,
) : RenderBufferAllocationStrategy {


    override fun allocate(renderBuffer: RenderBuffer, width: Int, height: Int) {
        renderBuffer.loadStorage(width, height, this.iFormat)
    }
}