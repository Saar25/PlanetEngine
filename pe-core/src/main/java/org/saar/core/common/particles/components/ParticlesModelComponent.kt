package org.saar.core.common.particles.components

import org.saar.core.common.particles.ParticlesModel
import org.saar.core.node.NodeComponent

class ParticlesModelComponent(val model: ParticlesModel) : NodeComponent {

    var instancesCount: Int = 0

}