package org.saar.core.light

data class Attenuation(
    val constant: Float = 1f,
    val linear: Float,
    val quadratic: Float,
)