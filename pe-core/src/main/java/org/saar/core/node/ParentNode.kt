package org.saar.core.node

interface ParentNode : Node {

    val children: List<Node>

    override fun update() {
        this.children.forEach { it.update() }
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}