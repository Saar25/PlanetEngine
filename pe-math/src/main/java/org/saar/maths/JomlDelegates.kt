package org.saar.maths

import org.joml.*
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3
import org.saar.maths.utils.Vector4
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object JomlDelegates {

    class CachedMatrix4f(private val value: Matrix4f = Matrix4.create()) : ReadWriteProperty<Any?, Matrix4fc> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = this.value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Matrix4fc) {
            this.value.set(value)
        }
    }

    class CachedVector4f(private val value: Vector4f = Vector4.create()) : ReadWriteProperty<Any?, Vector4fc> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = this.value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Vector4fc) {
            this.value.set(value)
        }
    }

    class CachedVector3f(private val value: Vector3f = Vector3.create()) : ReadWriteProperty<Any?, Vector3fc> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = this.value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Vector3fc) {
            this.value.set(value)
        }
    }

    class CachedVector2f(private val value: Vector2f = Vector2.create()) : ReadWriteProperty<Any?, Vector2fc> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = this.value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Vector2fc) {
            this.value.set(value)
        }
    }

    class CachedVector4i(private val value: Vector4i = Vector4i()) : ReadWriteProperty<Any?, Vector4ic> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = this.value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Vector4ic) {
            this.value.set(value)
        }
    }

    class CachedVector3i(private val value: Vector3i = Vector3i()) : ReadWriteProperty<Any?, Vector3ic> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = this.value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Vector3ic) {
            this.value.set(value)
        }
    }

    class CachedVector2i(private val value: Vector2i = Vector2i()) : ReadWriteProperty<Any?, Vector2ic> {
        override fun getValue(thisRef: Any?, property: KProperty<*>) = this.value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Vector2ic) {
            this.value.set(value)
        }
    }

}
