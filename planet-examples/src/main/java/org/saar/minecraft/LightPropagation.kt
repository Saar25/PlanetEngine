package org.saar.minecraft

data class LightPropagation(val side: Int, val down: Int) {
    companion object {
        @JvmField
        val NONE = LightPropagation(0xFF, 0xFF)
    }
}