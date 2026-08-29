package org.saar.rhi.shader

class GlslVersion(private val major: Int, private val minor: Int, private val type: Type) {

    enum class Type(val value: String) {
        NONE(""),
        CORE("core"),
        COMPATIBILITY("compatibility"),
    }

    override fun toString(): String {
        val type = this.type.value
        val version = "${this.major}${this.minor}0"
        return "#version $version $type"
    }

    companion object {
        val NONE: GlslVersion = GlslVersion(0, 0, Type.NONE)
        val V110: GlslVersion = GlslVersion(1, 1, Type.NONE)
        val V120: GlslVersion = GlslVersion(1, 2, Type.NONE)
        val V130: GlslVersion = GlslVersion(1, 3, Type.NONE)
        val V140: GlslVersion = GlslVersion(1, 4, Type.NONE)
        val V150: GlslVersion = GlslVersion(1, 5, Type.NONE)
        val V330: GlslVersion = GlslVersion(3, 3, Type.NONE)
        val V400: GlslVersion = GlslVersion(4, 0, Type.NONE)
        val V410: GlslVersion = GlslVersion(4, 1, Type.NONE)
        val V420: GlslVersion = GlslVersion(4, 2, Type.NONE)
        val V430: GlslVersion = GlslVersion(4, 3, Type.NONE)
        val V440: GlslVersion = GlslVersion(4, 4, Type.NONE)
        val V450: GlslVersion = GlslVersion(4, 5, Type.NONE)
        val V460: GlslVersion = GlslVersion(4, 6, Type.NONE)
    }
}