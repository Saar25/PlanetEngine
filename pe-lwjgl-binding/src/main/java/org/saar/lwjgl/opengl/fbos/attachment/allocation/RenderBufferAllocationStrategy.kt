package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

interface RenderBufferAllocationStrategy {

    fun allocate(renderBuffer: RenderBuffer, width: Int, height: Int)

}