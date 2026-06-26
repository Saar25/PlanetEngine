package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.FboBlitFilter
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

interface Screen {

    val width: Int

    val height: Int

    fun copyTo(other: Screen,
               filter: FboBlitFilter = FboBlitFilter.LINEAR,
               vararg buffers: GlBuffer = arrayOf(GlBuffer.COLOR))

    fun setAsDraw()
}

fun Screen.copyTo(other: Screen) = copyTo(other, FboBlitFilter.LINEAR, GlBuffer.COLOR)

fun Screen.clear(vararg buffers: GlBuffer) = this.clear(buffers.asIterable())

fun Screen.clear(buffers: Iterable<GlBuffer>) {
    this.setAsDraw()
    GlUtils.clear(buffers)
}