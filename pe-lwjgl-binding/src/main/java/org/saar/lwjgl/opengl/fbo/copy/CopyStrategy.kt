package org.saar.lwjgl.opengl.fbo.copy

import org.saar.lwjgl.opengl.fbo.DrawableFbo
import org.saar.lwjgl.opengl.fbo.ReadableFbo

interface CopyStrategy {

    fun copy(from: ReadableFbo, to: DrawableFbo)

}