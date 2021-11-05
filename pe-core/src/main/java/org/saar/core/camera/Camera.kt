package org.saar.core.camera

import org.saar.core.common.components.TransformComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponentGroup
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.utils.Matrix4

class Camera(override val projection: Projection,
             components: NodeComponentGroup = NodeComponentGroup()
) : ICamera, ComposableNode {

    constructor(projection: Projection) : this(projection, NodeComponentGroup())

    override val transform = SimpleTransform()

    override val components = NodeComponentGroup(components, TransformComponent(this.transform))

    override val viewMatrix = Matrix4.create()
        get() = Matrix4.ofView(
            this.transform.position.value,
            this.transform.rotation.value,
            field
        )

    init {
        this.components.start(this)
    }

    override fun update() = components.update(this)

    override fun delete() = components.delete(this)
}