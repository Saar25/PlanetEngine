package org.saar.gui.style.length

import org.saar.gui.UIChildNode

interface LengthValue {

    /**
     * Compute the width of the given node
     *
     * @param container the node
     * @return the width of the node in pixels
     */
    fun computeAxisX(container: UIChildNode): Int

    /**
     * Compute the height of the given node
     *
     * @param container the node
     * @return the height of the node in pixels
     */
    fun computeAxisY(container: UIChildNode): Int

    /**
     * Compute the min width of the given node
     * Call this method from the parent node
     * The computed value should not depend on the parent
     *
     * @param container the node
     * @return the min width of the node in pixels
     */
    fun computeMinAxisX(container: UIChildNode): Int

    /**
     * Compute the min height of the given node
     * Call this method from the parent node
     * The computed value should not depend on the parent
     *
     * @param container the node
     * @return the min height of the node in pixels
     */
    fun computeMinAxisY(container: UIChildNode): Int

    /**
     * Compute the max width of the given node
     * Call this method from a child node
     * The computed value should not depend on any child
     *
     * @param container the node
     * @return the max width of the node in pixels
     */
    fun computeMaxAxisX(container: UIChildNode): Int

    /**
     * Compute the max height of the given node
     * Call this method from a child node
     * The computed value should not depend on any child
     *
     * @param container the node
     * @return the max height of the node in pixels
     */
    fun computeMaxAxisY(container: UIChildNode): Int
}