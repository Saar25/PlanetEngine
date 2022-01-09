package org.saar.gui.style.length

object NoLength : ReadonlyLength {
    override fun get() = 0

    override fun getMin() = 0
}