package org.saar.lwjgl.opengl.fbo.copy

import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo

interface CopyStrategy {

    fun copy(from: ReadOnlyFbo, to: ReadOnlyFbo)

}