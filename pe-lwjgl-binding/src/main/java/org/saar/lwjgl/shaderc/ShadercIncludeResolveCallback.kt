package org.saar.lwjgl.shaderc

import org.lwjgl.system.MemoryUtil
import org.lwjgl.util.shaderc.ShadercIncludeResolveI
import org.saar.rhi.shader.ShaderStageType

fun interface ShadercIncludeResolveCallback {
    operator fun invoke(
        userData: Long,
        requestedSource: String,
        type: ShaderStageType,
        requestingSource: String,
        includeDepth: Long
    ): ShadercIncludeResult
}


fun ShadercIncludeResolveCallback.toShaderc(): ShadercIncludeResolveI = { userData: Long,
                                                                          requestedSource: Long,
                                                                          type: Int,
                                                                          requestingSource: Long,
                                                                          includeDepth: Long ->
    val parsedRequestedSource = MemoryUtil.memUTF8(requestedSource)
    val parsedRequestingSource = MemoryUtil.memUTF8(requestingSource)
    val parsedType = parseShadercShaderType(type)

    this(userData, parsedRequestedSource, parsedType, parsedRequestingSource, includeDepth).address()
}