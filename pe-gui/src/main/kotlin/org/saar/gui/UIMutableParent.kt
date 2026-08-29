package org.saar.gui

interface UIMutableParent : UIParentNode {

    override val children: List<UINode>

    fun add(uiNode: UIChildNode)

    fun remove(uiNode: UIChildNode)

    operator fun <T : UIChildNode> T.unaryPlus() = this@unaryPlus.also(::add)
}
