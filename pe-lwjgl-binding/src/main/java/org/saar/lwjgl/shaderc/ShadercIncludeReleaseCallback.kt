package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.ShadercIncludeResultReleaseI

fun interface ShadercIncludeReleaseCallback {
    operator fun invoke(
        userData: Long,
        includeResult: ShadercIncludeResult
    )
}

fun ShadercIncludeReleaseCallback.toShaderc(): ShadercIncludeResultReleaseI = { userData: Long,
                                                                                includeResult: Long ->
    this(userData, ShadercIncludeResult.of(includeResult))
}