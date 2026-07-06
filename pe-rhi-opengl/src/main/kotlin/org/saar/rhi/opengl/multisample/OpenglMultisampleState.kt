package org.saar.rhi.opengl.multisample

import org.lwjgl.opengl.ARBSampleShading
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL32
import org.saar.rhi.multisample.MultisampleState

fun MultisampleState.toOpengl() = OpenglMultisampleState(this)

class OpenglMultisampleState(private val state: MultisampleState) {

    // TODO: use this when creating the fbo attachments
    val rasterizationSamples: Int? by state::rasterizationSamplesBits

    fun set() {
        if (state.sampleShadingEnable ?: false) {
            GL13.glEnable(ARBSampleShading.GL_SAMPLE_SHADING_ARB)
            state.minSampleShading?.let { ARBSampleShading.glMinSampleShadingARB(it) }
        } else {
            GL13.glDisable(ARBSampleShading.GL_SAMPLE_SHADING_ARB)
        }

        if (state.alphaToCoverageEnable ?: false) {
            GL13.glEnable(GL13.GL_SAMPLE_ALPHA_TO_COVERAGE)
        } else {
            GL13.glDisable(GL13.GL_SAMPLE_ALPHA_TO_COVERAGE)
        }

        if (state.alphaToOneEnable ?: false) {
            GL13.glEnable(GL13.GL_SAMPLE_ALPHA_TO_ONE)
        } else {
            GL13.glDisable(GL13.GL_SAMPLE_ALPHA_TO_ONE)
        }

        /*state.sampleMask?.let { mask ->
            GL32.glSampleMaski(0, mask)
        }*/
    }
}
