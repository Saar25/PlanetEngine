package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.BlockStyle
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun UIBlock(block: UIBlock.() -> Unit): UIBlock {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return UIBlock().apply(block)
}

class UIBlock : UIChildNode {

    override var parent: UIParentNode = UINullNode

    override val style = BlockStyle(this)

    override val uiInputHelper = UIInputHelper(this)

    override fun render(context: RenderContext) = UIBlockRenderer.render(context, this)

    override fun contains(x: Int, y: Int) = MouseDetection.contains(this, x, y)

    override fun delete() {
        this.style.backgroundImage.texture.delete()
        this.style.discardMap.texture.delete()
    }
}
