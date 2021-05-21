package org.saar.gui

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.gui.font.Font
import org.saar.gui.font.FontCharacter
import org.saar.gui.render.LetterRenderer
import org.saar.gui.style.Style

class UILetter(override val parent: UIText, val font: Font,
               char: Char, val xAdvance: Float) : UIChildElement, Model {

    override val style: Style = Style(this)

    override val mesh: Mesh = QuadMesh

    val character: FontCharacter = this.font.getCharacterOrDefault(char)

    fun render(context: RenderContext) {
        LetterRenderer.render(context, this)
    }
}