package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc

enum class ShadercProfile(val value: Int) {
    NONE(Shaderc.shaderc_profile_none),
    CORE(Shaderc.shaderc_profile_core),
    COMPATIBILITY(Shaderc.shaderc_profile_compatibility),
    ES(Shaderc.shaderc_profile_es),
}
