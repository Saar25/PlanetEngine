package org.saar.minecraft.threading;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

class QueueElement<T> {

    private final Callable<T> callable;
    private final CompletableFuture<T> future;

    public QueueElement(Callable<T> callable, CompletableFuture<T> future) {
        this.callable = callable;
        this.future = future;
    }

    public void run() throws Exception {
        final T call = this.callable.call();
        this.future.complete(call);
    }

    public CompletableFuture<T> getFuture() {
        return this.future;
    }
}
