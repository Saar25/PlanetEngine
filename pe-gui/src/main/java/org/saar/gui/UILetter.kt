package org.saar.gui

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.gui.font.Font
import org.saar.gui.font.FontCharacter
import org.saar.gui.font.getCharacterOrDefault
import org.saar.gui.render.LetterRenderer
import org.saar.gui.style.Style

class UILetter(parent: UIText, val font: Font, character: Char) : UIChildNode, Model {

    override var parent: UIElement = parent
    val text = parent

    override val style: Style = Style(this)

    override val mesh: Mesh = QuadMesh

    override val uiBlocks = mutableListOf<UIBlock>()

    val character: FontCharacter = this.font.getCharacterOrDefault(character)

    override fun render(context: RenderContext) {
        LetterRenderer.render(context, this)
    }

    override fun delete() {
        super<UIChildNode>.delete()
    }
}