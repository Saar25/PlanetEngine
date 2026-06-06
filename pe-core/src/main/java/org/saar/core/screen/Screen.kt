package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.FboBlitFilter
import org.saar.lwjgl.opengl.utils.GlBuffer

interface Screen {

    val width: Int

    val height: Int

    fun copyTo(other: Screen,
               filter: FboBlitFilter = FboBlitFilter.LINEAR,
               vararg buffers: GlBuffer = arrayOf(GlBuffer.COLOUR))

    fun setAsDraw()
}

fun Screen.copyTo(other: Screen) = copyTo(other, FboBlitFilter.LINEAR, GlBuffer.COLOUR)