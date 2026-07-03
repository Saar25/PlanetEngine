package org.saar.core.util

import java.time.Duration

class Time {

    private var last: Long = System.currentTimeMillis()

    fun delta(): Duration = Duration.ofMillis(
        System.currentTimeMillis() - this.last)

    fun update() {
        this.last = System.currentTimeMillis()
    }

}