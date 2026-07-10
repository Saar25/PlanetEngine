package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc

enum class ShadercOptimizationLevel(val value: Int) {
    NONE(Shaderc.shaderc_optimization_level_zero),
    SIZE(Shaderc.shaderc_optimization_level_size),
    PERFORMANCE(Shaderc.shaderc_optimization_level_performance),
}
