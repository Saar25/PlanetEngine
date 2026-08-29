package org.saar.maths.noise

fun Noise1f.layered(layers: Int) = LayeredNoise1f(this, layers)
fun Noise2f.layered(layers: Int) = LayeredNoise2f(this, layers)
fun Noise3f.layered(layers: Int) = LayeredNoise3f(this, layers)

fun Noise1f.multiplied(multiply: Float) = MultipliedNoise1f(this, multiply)
fun Noise2f.multiplied(multiply: Float) = MultipliedNoise2f(this, multiply)
fun Noise3f.multiplied(multiply: Float) = MultipliedNoise3f(this, multiply)

fun Noise1f.offset(offset: Float) = OffsetNoise1f(this, offset)
fun Noise2f.offset(offset: Float) = OffsetNoise2f(this, offset)
fun Noise3f.offset(offset: Float) = OffsetNoise3f(this, offset)

fun Noise1f.spread(division: Float) = SpreadNoise1f(this, division)
fun Noise2f.spread(division: Float) = SpreadNoise2f(this, division)
fun Noise3f.spread(division: Float) = SpreadNoise3f(this, division)