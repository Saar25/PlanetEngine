package org.saar.gui.style

import org.saar.gui.style.alignment.ReadonlyAlignment
import org.saar.gui.style.arrangement.ReadonlyArrangement
import org.saar.gui.style.axisalignment.ReadonlyAxisAlignment
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

interface ParentStyle : Style {

    override val position: ReadonlyPosition

    override val margin: ReadonlyMargin

    override val padding: ReadonlyPadding

    override val x: ReadonlyCoordinate

    override val y: ReadonlyCoordinate

    override val width: ReadonlyLength

    override val height: ReadonlyLength

    override val boxSizing: ReadonlyBoxSizing

    override val fontSize: ReadonlyFontSize

    override val fontColor: ReadonlyFontColor

    override val font: ReadonlyFontFamily

    override val colorModifier: ReadonlyColorModifier

    override val borders: ReadonlyBorders

    override val borderColor: ReadonlyBorderColor

    override val radius: ReadonlyRadius

    override val opacity: ReadonlyOpacity

    override val backgroundColor: ReadonlyBackgroundColor

    override val backgroundImage: ReadonlyBackgroundImage

    override val discardMap: ReadonlyDiscardMap

    val alignment: ReadonlyAlignment

    val arrangement: ReadonlyArrangement

    val axisAlignment: ReadonlyAxisAlignment
}