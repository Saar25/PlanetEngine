package org.saar.gui.style.length

fun interface ReadonlyTextLength : ReadonlyLength {

    override fun get(): Int

}