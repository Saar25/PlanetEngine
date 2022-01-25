package org.saar.gui.style.position

interface ReadonlyPosition {

    val value: PositionValue

    fun getX(): Int

    fun getY(): Int

}