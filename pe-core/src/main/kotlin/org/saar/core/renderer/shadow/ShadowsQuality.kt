package org.saar.core.renderer.shadow

class ShadowsQuality private constructor(@JvmField val imageSize: Int) {
    companion object {
        val VERY_LOW: ShadowsQuality = ofLevel(9)

        val LOW: ShadowsQuality = ofLevel(10)

        @JvmField
        val MEDIUM: ShadowsQuality = ofLevel(11)

        val HIGH: ShadowsQuality = ofLevel(12)

        val VERY_HIGH: ShadowsQuality = ofLevel(13)

        fun ofLevel(level: Int) = ShadowsQuality(1 shl level)
    }
}
