package org.saar.maths

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector2i
import org.joml.Vector3f
import org.joml.Vector3i
import org.joml.Vector4f
import org.joml.Vector4i
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class JomlDelegatesTest {

    private class Vector2fHolder {
        var value: Vector2f by JomlDelegates.CachedVector2f()
    }

    private class Vector3fHolder {
        var value: Vector3f by JomlDelegates.CachedVector3f()
    }

    private class Vector4fHolder {
        var value: Vector4f by JomlDelegates.CachedVector4f()
    }

    private class Vector2iHolder {
        var value: Vector2i by JomlDelegates.CachedVector2i()
    }

    private class Vector3iHolder {
        var value: Vector3i by JomlDelegates.CachedVector3i()
    }

    private class Vector4iHolder {
        var value: Vector4i by JomlDelegates.CachedVector4i()
    }

    private class Matrix4fHolder {
        var value: Matrix4f by JomlDelegates.CachedMatrix4f()
    }

    @Test
    fun `CachedVector2f returns the same instance and updates on set`() {
        val holder = Vector2fHolder()

        val first = holder.value
        holder.value = Vector2f(1f, 2f)
        val second = holder.value

        assertSame(first, second)
        assertEquals(1f, second.x(), 0f)
        assertEquals(2f, second.y(), 0f)
    }

    @Test
    fun `CachedVector3f returns the same instance and updates on set`() {
        val holder = Vector3fHolder()

        val first = holder.value
        holder.value = Vector3f(1f, 2f, 3f)

        assertSame(first, holder.value)
        assertEquals(3f, holder.value.z(), 0f)
    }

    @Test
    fun `CachedVector4f returns the same instance and updates on set`() {
        val holder = Vector4fHolder()

        val first = holder.value
        holder.value = Vector4f(1f, 2f, 3f, 4f)

        assertSame(first, holder.value)
        assertEquals(4f, holder.value.w(), 0f)
    }

    @Test
    fun `CachedVector2i returns the same instance and updates on set`() {
        val holder = Vector2iHolder()

        val first = holder.value
        holder.value = Vector2i(1, 2)

        assertSame(first, holder.value)
        assertEquals(2, holder.value.y())
    }

    @Test
    fun `CachedVector3i returns the same instance and updates on set`() {
        val holder = Vector3iHolder()

        val first = holder.value
        holder.value = Vector3i(1, 2, 3)

        assertSame(first, holder.value)
        assertEquals(3, holder.value.z())
    }

    @Test
    fun `CachedVector4i returns the same instance and updates on set`() {
        val holder = Vector4iHolder()

        val first = holder.value
        holder.value = Vector4i(1, 2, 3, 4)

        assertSame(first, holder.value)
        assertEquals(4, holder.value.w())
    }

    @Test
    fun `CachedMatrix4f returns the same instance and updates on set`() {
        val holder = Matrix4fHolder()

        val first = holder.value
        holder.value = Matrix4f().translation(1f, 2f, 3f)

        assertSame(first, holder.value)
        assertEquals(3f, holder.value.m32(), 0f)
    }
}
