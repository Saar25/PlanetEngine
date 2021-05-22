package org.saar.gui.style

import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.length.ReadonlyTextLength

interface ITextStyle : IStyle {

    override val width: ReadonlyTextLength

    override val height: ReadonlyTextLength

    val fontSize: ReadonlyLength

    val contentWidth: ReadonlyLength

    val contentHeight: ReadonlyLength

}