package org.saar.rhi.opengl.shader

import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL32
import org.lwjgl.opengl.GL40
import org.lwjgl.opengl.GL43
import org.saar.rhi.shader.ShaderStageType

val ShaderStageType.glValue
    get() = when (this) {
        ShaderStageType.VERTEX -> GL20.GL_VERTEX_SHADER
        ShaderStageType.TESSELLATION_CONTROL -> GL40.GL_TESS_CONTROL_SHADER
        ShaderStageType.TESSELLATION_EVALUATION -> GL40.GL_TESS_EVALUATION_SHADER
        ShaderStageType.GEOMETRY -> GL32.GL_GEOMETRY_SHADER
        ShaderStageType.FRAGMENT -> GL20.GL_FRAGMENT_SHADER
        ShaderStageType.COMPUTE -> GL43.GL_COMPUTE_SHADER
    }


