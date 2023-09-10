package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderingPath
import org.saar.core.renderer.SimpleRenderingPath

class DeferredRenderingPath(
    camera: ICamera,
    pipeline: DeferredRenderingPipeline,
) : RenderingPath<DeferredRenderingBuffers> {

    val prototype = DeferredScreenPrototype()

    private val path = SimpleRenderingPath(camera, pipeline, this.prototype)

    override fun render() = this.path.render()

    override fun delete() = this.path.delete()
}