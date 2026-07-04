package org.saar.rhi.resterization

enum class CullMode {
    NONE,
    FRONT,
    BACK,
    FRONT_AND_BACK,
    ;

    val opposite
        get() = when (this) {
            NONE -> FRONT_AND_BACK
            FRONT -> BACK
            BACK -> FRONT
            FRONT_AND_BACK -> NONE
        }
}