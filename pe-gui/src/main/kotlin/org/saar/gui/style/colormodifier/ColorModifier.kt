package org.saar.gui.style.colormodifier

import org.joml.Vector4fc
import org.saar.gui.UIChildNode
import org.saar.maths.utils.Vector4

class ColorModifier(private val container: UIChildNode, default: ColorModifierValue = ColorModifierValues.inherit) : ReadonlyColorModifier {

    private var multiplyValue: ColorModifierValue = default

    override var multiply: Vector4fc
        get() = this.multiplyValue.compute(this.container)
        set(value) {
            this.multiplyValue = ColorModifierValues.of(value)
        }

    fun set(multiply: Vector4fc) {
        this.multiply = multiply
    }

    fun set(r: Float, g: Float, b: Float, a: Float) {
        this.multiply = Vector4.of(r, g, b, a)
    }

    fun set(r: Float, g: Float, b: Float) {
        this.multiply = Vector4.of(r, g, b, 1f)
    }

    fun set(all: Float) {
        this.multiply = Vector4.of(all, all, all, 1f)
    }

    fun set(multiplyValue: ColorModifierValue) {
        this.multiplyValue = multiplyValue
    }
}