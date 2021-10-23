package org.saar.core.renderer.forward

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderingPath
import org.saar.core.renderer.SimpleRenderingPath

class ForwardRenderingPath(
    camera: ICamera,
    pipeline: ForwardRenderingPipeline
) : RenderingPath<ForwardRenderingBuffers> {

    private val path = SimpleRenderingPath(camera, pipeline)

    override fun render() = this.path.render()

    override fun delete() = this.path.delete()
}