package org.saar.core.common.components

import org.joml.Vector3f
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.core.util.Time
import org.saar.maths.utils.Vector3

class AccelerationComponent : NodeComponent {

    private lateinit var velocityComponent: VelocityComponent

    private val temp: Vector3f = Vector3.create()
    private val time: Time = Time()

    val direction: Vector3f = Vector3.create()

    override fun start(node: ComposableNode) {
        this.velocityComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val delta = this.time.delta().toMillis() / 1000f
        this.direction.mul(delta, this.temp)

        this.velocityComponent.direction.add(this.temp)

        this.time.update()
    }
}