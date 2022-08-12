package org.saar.lwjgl.opengl.fbos.copy

import org.saar.lwjgl.opengl.fbos.DrawableFbo
import org.saar.lwjgl.opengl.fbos.ReadableFbo

interface CopyStrategy {

    fun copy(from: ReadableFbo, to: DrawableFbo)

}