package org.saar.core.common.terrain.height

class FlatHeightGenerator(private val height: Float) : HeightGenerator {

    override fun generate(x: Float, y: Float, z: Float) = this.height

}