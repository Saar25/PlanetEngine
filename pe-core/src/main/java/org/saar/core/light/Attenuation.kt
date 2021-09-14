package org.saar.core.light

data class Attenuation(
    val constant: Float = 1f,
    val linear: Float,
    val quadratic: Float,
) {
    companion object {
        val DISTANCE_7 = Attenuation(1.0f, 0.7f, 1.8f)
        val DISTANCE_13 = Attenuation(1.0f, 0.35f, 0.44f)
        val DISTANCE_20 = Attenuation(1.0f, 0.22f, 0.20f)
        val DISTANCE_32 = Attenuation(1.0f, 0.14f, 0.07f)
        val DISTANCE_50 = Attenuation(1.0f, 0.09f, 0.032f)
        val DISTANCE_65 = Attenuation(1.0f, 0.07f, 0.017f)
        val DISTANCE_100 = Attenuation(1.0f, 0.045f, 0.0075f)
        val DISTANCE_160 = Attenuation(1.0f, 0.027f, 0.0028f)
        val DISTANCE_200 = Attenuation(1.0f, 0.022f, 0.0019f)
        val DISTANCE_325 = Attenuation(1.0f, 0.014f, 0.0007f)
        val DISTANCE_600 = Attenuation(1.0f, 0.007f, 0.0002f)
        val DISTANCE_3250 = Attenuation(1.0f, 0.0014f, 0.000007f)
    }
}