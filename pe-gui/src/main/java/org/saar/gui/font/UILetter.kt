package org.saar.gui.font

import org.joml.Vector2fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.core.mesh.common.QuadMesh
import org.saar.gui.UIChildElement
import org.saar.gui.UIText
import org.saar.gui.style.IStyle
import org.saar.gui.style.Style

class UILetter(override val parent: UIText, val font: Font,
               val character: FontCharacter, val advance: Vector2fc) : UIChildElement, Model {

    override val style: IStyle = Style(this)

    override val mesh: Mesh = QuadMesh
}