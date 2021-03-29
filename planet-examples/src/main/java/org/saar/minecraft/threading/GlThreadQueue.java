package org.saar.minecraft.threading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public final class GlThreadQueue {

    private static final GlThreadQueue INSTANCE = new GlThreadQueue();

    private final Queue<QueueElement<?>> queue = new LinkedList<>();

    private GlThreadQueue() {
    }

    public static GlThreadQueue getInstance() {
        return GlThreadQueue.INSTANCE;
    }

    public void run() throws Exception {
        QueueElement<?> element;
        while ((element = queue.poll()) != null) {
            element.run();
        }
    }

    public CompletableFuture<?> supply(Runnable callable) {
        return supply(() -> {
            callable.run();
            return null;
        });
    }

    public <T> CompletableFuture<T> supply(Callable<T> callable) {
        final QueueElement<T> queueElement = new QueueElement<>(
                callable, new CompletableFuture<>());
        this.queue.offer(queueElement);
        return queueElement.getFuture();
    }

}
