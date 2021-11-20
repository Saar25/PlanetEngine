package org.saar.core.light

import org.joml.Vector3f
import org.saar.core.node.NodeComponentGroup
import org.saar.core.node.ComposableNode
import org.saar.core.common.components.TransformComponent
import org.saar.core.node.Node
import org.saar.maths.utils.Vector3

class PointLight(override val components: NodeComponentGroup = NodeComponentGroup()) : IPointLight, ComposableNode, Node {
    override val position: Vector3f = Vector3.create()
    override var attenuation: Attenuation = Attenuation.DISTANCE_7
    override val colour: Vector3f = Vector3.create()

    init {
        this.components.start(this)
    }

    override fun update() {
        this.components.update(this)
        this.components.getNullable(TransformComponent::class)?.also {
            this.position.set(it.transform.position.value)
        }
    }

    override fun delete() {
        this.components.delete(this)
    }
}