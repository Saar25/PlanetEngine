package org.saar.maths

import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

object MoreDelegates {

    fun clamp(initial: Float, min: Float = 0f, max: Float = 1f) = object : ObservableProperty<Float>(initial) {
        override fun beforeChange(property: KProperty<*>, oldValue: Float, newValue: Float): Boolean {
            return (min..max).contains(newValue)
        }
    }

    fun clamp(initial: Int, min: Int = 0, max: Int = 1) = object : ObservableProperty<Int>(initial) {
        override fun beforeChange(property: KProperty<*>, oldValue: Int, newValue: Int): Boolean {
            return (min..max).contains(newValue)
        }
    }
}