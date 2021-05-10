package org.saar.core.postprocessing

interface PostProcessor {

    fun process(buffers: PostProcessingBuffers)

    fun delete()

}