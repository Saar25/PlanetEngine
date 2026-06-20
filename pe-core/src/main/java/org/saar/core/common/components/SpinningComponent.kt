package org.saar.core.common.components

import org.joml.Quaternionfc
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent

class SpinningComponent(private val rotation: Quaternionfc) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        this.transformComponent.transform.rotation.rotateDegrees(0f, 1f, 0f)
    }
}