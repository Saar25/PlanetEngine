package org.saar.minecraft.threading

import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture

internal class QueueElement<T>(private val callable: Callable<T>, val future: CompletableFuture<T>) {
    @Throws(Exception::class)
    fun run() {
        val call = this.callable.call()
        this.future.complete(call)
    }
}
