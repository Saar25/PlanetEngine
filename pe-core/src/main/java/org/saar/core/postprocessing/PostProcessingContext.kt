package org.saar.core.postprocessing

import org.saar.core.camera.ICamera

class PostProcessingContext(
    val camera: ICamera?,
    val buffers: PostProcessingBuffers
)