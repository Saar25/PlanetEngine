package org.saar.core.behavior

interface Behavior {

    /**
     * Initialize the behavior
     *
     * @param node the node that is influenced by the behavior
     */
    fun start(node: BehaviorNode) {
    }

    /**
     * Update the behavior
     *
     * @param node the node that is influenced by the behavior
     */
    fun update(node: BehaviorNode) {

    }
}