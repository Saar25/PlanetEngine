package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc
import org.saar.rhi.shader.ShaderStageType

fun parseShadercShaderType(type: Int): ShaderStageType = when (type) {
    Shaderc.shaderc_glsl_vertex_shader -> ShaderStageType.VERTEX
    Shaderc.shaderc_glsl_fragment_shader -> ShaderStageType.FRAGMENT
    Shaderc.shaderc_glsl_compute_shader -> ShaderStageType.COMPUTE
    Shaderc.shaderc_glsl_geometry_shader -> ShaderStageType.GEOMETRY
    Shaderc.shaderc_glsl_tess_control_shader -> ShaderStageType.TESSELLATION_CONTROL
    Shaderc.shaderc_glsl_tess_evaluation_shader -> ShaderStageType.TESSELLATION_EVALUATION
    else -> throw IllegalArgumentException("Unknown shader type: $type")
}

val ShaderStageType.shadercValue: Int
    get() = when (this) {
        ShaderStageType.VERTEX -> Shaderc.shaderc_glsl_vertex_shader
        ShaderStageType.FRAGMENT -> Shaderc.shaderc_glsl_fragment_shader
        ShaderStageType.COMPUTE -> Shaderc.shaderc_glsl_compute_shader
        ShaderStageType.GEOMETRY -> Shaderc.shaderc_glsl_geometry_shader
        ShaderStageType.TESSELLATION_CONTROL -> Shaderc.shaderc_glsl_tess_control_shader
        ShaderStageType.TESSELLATION_EVALUATION -> Shaderc.shaderc_glsl_tess_evaluation_shader
    }