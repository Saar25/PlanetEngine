package org.saar.core.behavior

import org.saar.core.node.Node

interface BehaviorNode : Node {

    val behaviors: BehaviorGroup

}