package org.saar.core.common.components

import org.saar.core.camera.ICamera
import org.saar.core.mesh.lod.ClampedInt
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent

class LevelOfDetailComponent(
    private val camera: ICamera,
    private val lod: ClampedInt,
    private val distances: IntArray
) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val position = this.transformComponent.transform.position.value
        val cameraPosition = this.camera.transform.position.value
        val distanceSquare = position.distanceSquared(cameraPosition)
        val lod = this.distances.indexOfFirst { it * it < distanceSquare }
            .let { if (it == -1) this.distances.size - 1 else it }
        this.lod.set(lod)
    }
}