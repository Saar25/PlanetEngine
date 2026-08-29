package org.saar.core.node

import org.saar.core.node.ComposableNode

interface NodeComponent {

    /**
     * Initialize the component
     *
     * @param node the node that is influenced by the component
     */
    fun start(node: ComposableNode) = Unit

    /**
     * Update the component
     *
     * @param node the node that is influenced by the component
     */
    fun update(node: ComposableNode) = Unit

    /**
     * Delete the component
     *
     * @param node the node that is influenced by the component
     */
    fun delete(node: ComposableNode) = Unit
}