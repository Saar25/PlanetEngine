package org.saar.lwjgl.opengl.objects.attributes

import org.saar.lwjgl.opengl.constants.DataType

object Attributes {

    @JvmStatic
    fun sumBytes(vararg attributes: IAttribute): Int {
        return attributes.sumOf { it.bytesPerVertex }
    }

    @JvmStatic
    fun of(attributeIndex: Int, componentCount: Int, dataType: DataType, normalized: Boolean): IAttribute {
        val linker: AttributeLinker = FloatAttributeLinker(
            componentCount, dataType, normalized)
        return Attribute(linker, attributeIndex)
    }

    @JvmStatic
    @JvmOverloads
    fun ofInstanced(attributeIndex: Int, componentCount: Int, dataType: DataType,
                    normalized: Boolean, instances: Int = 1): IAttribute {
        val linker: AttributeLinker = FloatAttributeLinker(
            componentCount, dataType, normalized)
        return InstancedAttribute(linker, attributeIndex, instances)
    }

    @JvmStatic
    fun ofInteger(attributeIndex: Int, componentCount: Int, dataType: DataType): IAttribute {
        val linker: AttributeLinker = IntegerAttributeLinker(
            componentCount, dataType)
        return Attribute(linker, attributeIndex)
    }

    @JvmStatic
    @JvmOverloads
    fun ofIntegerInstanced(attributeIndex: Int, componentCount: Int,
                           dataType: DataType, instances: Int = 1): IAttribute {
        val linker: AttributeLinker = IntegerAttributeLinker(
            componentCount, dataType)
        return InstancedAttribute(linker, attributeIndex, instances)
    }

}