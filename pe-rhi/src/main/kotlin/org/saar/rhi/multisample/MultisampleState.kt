package org.saar.rhi.multisample

data class MultisampleState(
    val rasterizationSamplesBits: Int? = null,
    val sampleShadingEnable: Boolean? = null,
    val minSampleShading: Float? = null,
    // val sampleMask: Int? = null, // TODO: Implement this
    val alphaToCoverageEnable: Boolean? = null,
    val alphaToOneEnable: Boolean? = null,
)
