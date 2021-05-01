package org.saar.core.node

interface ParentNode : Node {
    val children: List<Node>

    override fun render() {
        this.children.forEach { it.render() }
    }
}