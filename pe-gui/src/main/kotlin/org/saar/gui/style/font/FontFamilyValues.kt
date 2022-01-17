package org.saar.gui.style.font

import org.jproperty.constant.ConstantObjectProperty
import org.jproperty.map
import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader

object FontFamilyValues {

    @JvmStatic
    val inherit: FontFamilyValue = FontFamilyValue { container ->
        container.parentProperty.map { it.style.font.family.value }
    }

    @JvmStatic
    val default: FontFamilyValue = FontFamilyValue { ConstantObjectProperty(FontLoader.DEFAULT_FONT) }

    @JvmStatic
    fun of(value: Font): FontFamilyValue = FontFamilyValue { ConstantObjectProperty(value) }

}