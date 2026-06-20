package org.saar.maths

@JvmInline
value class Angle private constructor(val radians: Float) {

    val degrees: Float get() = Math.toDegrees(this.radians.toDouble()).toFloat()

    companion object {
        val Float.radians get() = Angle(this)

        val Float.degrees get() = Angle(Math.toRadians(this.toDouble()).toFloat())
    }
}