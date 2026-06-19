package org.saar.maths

class Angle private constructor(val radians: Float) {

    val degrees: Float get() = Math.toDegrees(this.radians.toDouble()).toFloat()

    companion object {
        fun radians(radians: Float) = Angle(radians)

        @JvmStatic
        fun degrees(degrees: Float) = Angle(Math.toRadians(degrees.toDouble()).toFloat())
    }
}

fun Float.radians() = Angle.radians(this)

fun Float.degrees() = Angle.degrees(this)