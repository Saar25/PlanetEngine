package org.saar.gui.font

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.core.mesh.common.QuadMesh
import org.saar.gui.UIChildElement
import org.saar.gui.UIText
import org.saar.gui.style.Style

class Letter(override val parent: UIText, val font: Font,
             char: Char, val xAdvance: Float) : UIChildElement, Model {

    override val style: Style = Style(this)

    override val mesh: Mesh = QuadMesh

    val character: FontCharacter = this.font.getCharacterOrDefault(char)
}