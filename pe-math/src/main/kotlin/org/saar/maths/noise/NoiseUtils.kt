package org.saar.maths.noise

fun Noise1f.multiplied(multiply: Float) = MultipliedNoise1f(multiply, this)
fun Noise2f.multiplied(multiply: Float) = MultipliedNoise2f(multiply, this)
fun Noise3f.multiplied(multiply: Float) = MultipliedNoise3f(multiply, this)

fun Noise1f.offset(offset: Float) = OffsetNoise1f(offset, this)
fun Noise2f.offset(offset: Float) = OffsetNoise2f(offset, this)
fun Noise3f.offset(offset: Float) = OffsetNoise3f(offset, this)

fun Noise1f.spread(division: Float) = SpreadNoise1f(division, this)
fun Noise2f.spread(division: Float) = SpreadNoise2f(division, this)
fun Noise3f.spread(division: Float) = SpreadNoise3f(division, this)