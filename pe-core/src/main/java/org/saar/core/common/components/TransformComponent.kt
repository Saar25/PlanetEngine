package org.saar.core.common.components

import org.saar.core.node.NodeComponent
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

class TransformComponent(val transform: Transform = SimpleTransform()) : NodeComponent