package org.saar.gui.style.value

import org.saar.gui.style.ContainerStyle
import org.saar.gui.style.IStyle

object AxisAlignmentValues {

    @JvmStatic
    fun start() = object : AxisAlignmentValue {
        override fun computeAxisX(parent: ContainerStyle, child: IStyle): Int = 0

        override fun computeAxisY(parent: ContainerStyle, child: IStyle): Int = 0
    }

    @JvmStatic
    fun end() = object : AxisAlignmentValue {
        override fun computeAxisX(parent: ContainerStyle, child: IStyle): Int {
            return parent.height.get() - child.height.get()
        }

        override fun computeAxisY(parent: ContainerStyle, child: IStyle): Int {
            return parent.width.get() - child.width.get()
        }
    }

    @JvmStatic
    fun center() = object : AxisAlignmentValue {
        override fun computeAxisX(parent: ContainerStyle, child: IStyle): Int {
            return (parent.height.get() - child.height.get()) / 2
        }

        override fun computeAxisY(parent: ContainerStyle, child: IStyle): Int {
            return (parent.width.get() - child.width.get()) / 2
        }
    }
}