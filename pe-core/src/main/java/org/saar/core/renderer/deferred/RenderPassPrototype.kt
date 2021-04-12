package org.saar.core.renderer.deferred

import org.saar.lwjgl.opengl.shaders.Shader

interface RenderPassPrototype {

    fun fragmentShader(): Shader

    fun onRender(context: RenderPassContext) {

    }

}