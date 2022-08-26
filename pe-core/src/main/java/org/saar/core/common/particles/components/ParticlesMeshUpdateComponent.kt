package org.saar.core.common.particles.components

import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent

class ParticlesMeshUpdateComponent : NodeComponent {

    private lateinit var modelComponent: ParticlesModelComponent

    override fun start(node: ComposableNode) {
        this.modelComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        //this.modelComponent.model.mesh.prototype.storeInstances(0, this.modelComponent.instancesCount)
    }
}