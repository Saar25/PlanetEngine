package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc

enum class ShadercSourceLanguage(val value: Int) {
    GLSL(Shaderc.shaderc_source_language_glsl),
    HLSL(Shaderc.shaderc_source_language_hlsl),
}
