package org.saar.core.behavior

interface Behavior {

    /**
     * Initialize the behavior
     *
     * @param node the node that is influenced by the behavior
     */
    fun start(node: BehaviorNode) = Unit

    /**
     * Update the behavior
     *
     * @param node the node that is influenced by the behavior
     */
    fun update(node: BehaviorNode) = Unit

    /**
     * Delete the behavior
     *
     * @param node the node that is influenced by the behavior
     */
    fun delete(node: BehaviorNode) = Unit
}