package org.saar.core.postprocessing

import org.saar.lwjgl.opengl.shaders.Shader

interface PostProcessorPrototype {

    fun fragmentShader(): Shader

    fun fragmentOutputs(): Array<String> {
        return arrayOf()
    }

    fun onRender() {
    }

    fun onDelete() {
    }

}