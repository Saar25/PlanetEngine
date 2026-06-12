package org.saar.core.common.components

import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.maths.transform.ReadonlyPosition

class FocusRotationComponent(private val focus: ReadonlyPosition) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        this.transformComponent.transform.lookAt(focus)
    }
}