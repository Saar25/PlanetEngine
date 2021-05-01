package org.saar.core.node

import org.saar.core.mesh.Model

interface Node {
    val model: Model
    fun render()
}