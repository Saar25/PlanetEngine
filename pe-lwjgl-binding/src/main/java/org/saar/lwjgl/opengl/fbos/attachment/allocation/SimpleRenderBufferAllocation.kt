package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

class SimpleRenderBufferAllocation(
    private val width: Int,
    private val height: Int,
    private val iFormat: InternalFormat,
) : RenderBufferAllocationStrategy {


    override fun allocate(renderBuffer: RenderBuffer) {
        renderBuffer.loadStorage(this.width, this.height, this.iFormat)
    }
}