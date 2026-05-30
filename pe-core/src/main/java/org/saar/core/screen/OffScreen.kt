package org.saar.core.screen

interface OffScreen : Screen {

    fun resize(width: Int, height: Int)

    fun assureSize(width: Int, height: Int) {
        if (width != this.width || height != this.height) {
            resize(width, height)
        }
    }

    fun resizeToMainScreen() = assureSize(MainScreen.width, MainScreen.height)

    fun delete()
}
