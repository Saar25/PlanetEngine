package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.FboBlitFilter
import org.saar.lwjgl.opengl.utils.GlBuffer

interface Screen {

    val width: Int

    val height: Int

    fun copyTo(other: Screen, filter: FboBlitFilter, vararg buffers: GlBuffer)

    fun setAsDraw()

    fun setAsRead()
}

fun Screen.copyTo(other: Screen) = copyTo(other, FboBlitFilter.LINEAR, GlBuffer.COLOUR)