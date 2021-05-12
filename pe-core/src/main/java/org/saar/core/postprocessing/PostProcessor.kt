package org.saar.core.postprocessing

interface PostProcessor {

    fun process(context: PostProcessingContext)

    fun delete()

}