package org.saar.core.util

fun measureTime(code: () -> Unit): Long {
    val start = System.currentTimeMillis()

    code()

    return System.currentTimeMillis() - start
}