package org.saar.lwjgl.opengl.attribute

import org.saar.lwjgl.opengl.attribute.divisor.InstancedAttributeDivisor
import org.saar.lwjgl.opengl.attribute.divisor.NoAttributeDivisor
import org.saar.lwjgl.opengl.attribute.pointer.FloatAttributePointer
import org.saar.lwjgl.opengl.attribute.pointer.IntegerAttributePointer
import org.saar.lwjgl.opengl.constants.DataType

object Attributes {

    @JvmStatic
    fun sumBytes(vararg attributes: IAttribute): Int {
        return attributes.sumOf { it.bytesPerVertex }
    }

    @JvmStatic
    fun of(index: Int, componentCount: Int, dataType: DataType, normalized: Boolean): IAttribute {
        val linker = FloatAttributePointer(componentCount, dataType, normalized)
        return Attribute(index, linker, NoAttributeDivisor())
    }

    @JvmStatic
    @JvmOverloads
    fun ofInstanced(
        index: Int, componentCount: Int, dataType: DataType,
        normalized: Boolean, instances: Int = 1,
    ): IAttribute {
        val linker = FloatAttributePointer(componentCount, dataType, normalized)
        val divisor = InstancedAttributeDivisor(instances)
        return Attribute(index, linker, divisor)
    }

    @JvmStatic
    fun ofInteger(index: Int, componentCount: Int, dataType: DataType): IAttribute {
        val linker = IntegerAttributePointer(componentCount, dataType)
        return Attribute(index, linker, NoAttributeDivisor())
    }

    @JvmStatic
    @JvmOverloads
    fun ofIntegerInstanced(
        index: Int, componentCount: Int,
        dataType: DataType, instances: Int = 1,
    ): IAttribute {
        val linker = IntegerAttributePointer(componentCount, dataType)
        val divisor = InstancedAttributeDivisor(instances)
        return Attribute(index, linker, divisor)
    }

}