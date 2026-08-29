package org.saar.gui.style

import org.saar.gui.style.backgroundcolor.ReadonlyBackgroundColor
import org.saar.gui.style.backgroundimage.ReadonlyBackgroundImage
import org.saar.gui.style.border.ReadonlyBorders
import org.saar.gui.style.bordercolor.ReadonlyBorderColor
import org.saar.gui.style.boxsizing.ReadonlyBoxSizing
import org.saar.gui.style.colormodifier.ReadonlyColorModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.discardmap.ReadonlyDiscardMap
import org.saar.gui.style.font.ReadonlyFontFamily
import org.saar.gui.style.fontcolor.ReadonlyFontColor
import org.saar.gui.style.fontsize.ReadonlyFontSize
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.margin.ReadonlyMargin
import org.saar.gui.style.opacity.ReadonlyOpacity
import org.saar.gui.style.padding.ReadonlyPadding
import org.saar.gui.style.position.ReadonlyPosition
import org.saar.gui.style.redius.ReadonlyRadius

interface Style {

    val position: ReadonlyPosition

    val padding: ReadonlyPadding

    val margin: ReadonlyMargin

    val x: ReadonlyCoordinate

    val y: ReadonlyCoordinate

    val width: ReadonlyLength

    val height: ReadonlyLength

    val boxSizing: ReadonlyBoxSizing

    val fontSize: ReadonlyFontSize

    val fontColor: ReadonlyFontColor

    val font: ReadonlyFontFamily

    val colorModifier: ReadonlyColorModifier

    val borders: ReadonlyBorders

    val borderColor: ReadonlyBorderColor

    val radius: ReadonlyRadius

    val opacity: ReadonlyOpacity

    val backgroundColor: ReadonlyBackgroundColor

    val backgroundImage: ReadonlyBackgroundImage

    val discardMap: ReadonlyDiscardMap
}