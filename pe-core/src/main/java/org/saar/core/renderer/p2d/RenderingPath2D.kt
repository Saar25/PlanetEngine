package org.saar.core.renderer.p2d

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderingPath
import org.saar.core.renderer.SimpleRenderingPath

class RenderingPath2D(camera: ICamera, pipeline: RenderingPipeline2D) : RenderingPath<RenderingBuffers2D> {

    private val path = SimpleRenderingPath(camera, pipeline)

    override fun render() = this.path.render()

    override fun delete() = this.path.delete()
}