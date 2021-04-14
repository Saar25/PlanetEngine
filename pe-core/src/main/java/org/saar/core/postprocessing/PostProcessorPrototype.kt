package org.saar.core.postprocessing

import org.saar.lwjgl.opengl.shaders.Shader

interface PostProcessorPrototype {

    fun fragmentShader(): Shader

    fun onRender(context: PostProcessingContext) {
    }

    fun onDelete() {
    }

}