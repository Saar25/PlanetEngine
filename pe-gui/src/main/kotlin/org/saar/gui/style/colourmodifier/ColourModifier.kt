package org.saar.gui.style.colourmodifier

import org.joml.Vector4fc
import org.saar.gui.UIChildNode
import org.saar.maths.utils.Vector4

class ColourModifier(private val container: UIChildNode) : ReadonlyColourModifier {

    private var multiplyValue: ColourModifierValue = ColourModifierValues.inherit

    override var multiply: Vector4fc
        get() = this.multiplyValue.compute(this.container)
        set(value) {
            this.multiplyValue = ColourModifierValues.of(value)
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

    fun set(multiplyValue: ColourModifierValue) {
        this.multiplyValue = multiplyValue
    }
}