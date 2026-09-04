package org.saar.minecraft.threading

import java.util.Queue
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentLinkedQueue

object GlThreadQueue {
    private val queue: Queue<QueueElement<*>> = ConcurrentLinkedQueue()

    @Throws(Exception::class)
    fun run() {
        var element = this.queue.poll()
        while (element != null) {
            element.run()
            element = this.queue.poll()
        }
    }

    fun <T> supply(callable: Callable<T>): CompletableFuture<T> {
        val queueElement = QueueElement(
            callable, CompletableFuture()
        )
        this.queue.offer(queueElement)
        return queueElement.future
    }
}
