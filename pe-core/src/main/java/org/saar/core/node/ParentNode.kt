package org.saar.core.node

interface ParentNode : Node {
    val children: List<Node>
}