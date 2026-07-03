package org.saar.core.mesh.async;

import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.UnloadedMesh;

import java.util.concurrent.CompletableFuture;

abstract class FutureMeshHelper implements Mesh {

    public abstract FutureMeshHelper next();

    @Override
    public abstract void draw();

    @Override
    public abstract void delete();

    public static FutureMeshHelper create(CompletableFuture<? extends Mesh> task) {
        return new Running(task);
    }

    public static FutureMeshHelper unloaded(CompletableFuture<? extends UnloadedMesh> task) {
        return new Unloaded(task);
    }

    private static class Running extends FutureMeshHelper {

        private final CompletableFuture<? extends Mesh> task;

        public Running(CompletableFuture<? extends Mesh> task) {
            this.task = task;
        }

        @Override
        public FutureMeshHelper next() {
            if (this.task.isDone()) {
                final Mesh mesh = this.task.getNow(null);
                return new Finished(mesh);
            }
            return this;
        }

        @Override
        public void draw() {
        }

        @Override
        public void delete() {
            this.task.cancel(true);
        }
    }

    private static class Unloaded extends FutureMeshHelper {

        private final CompletableFuture<? extends UnloadedMesh> task;

        public Unloaded(CompletableFuture<? extends UnloadedMesh> task) {
            this.task = task;
        }

        @Override
        public FutureMeshHelper next() {
            if (this.task.isDone()) {
                final UnloadedMesh unloaded = this.task.getNow(null);
                final Mesh mesh = unloaded.load();
                return new Finished(mesh);
            }
            return this;
        }

        @Override
        public void draw() {
        }

        @Override
        public void delete() {
            this.task.cancel(true);
        }
    }

    private static class Finished extends FutureMeshHelper {

        private final Mesh mesh;

        public Finished(Mesh mesh) {
            this.mesh = mesh;
        }

        @Override
        public FutureMeshHelper next() {
            return this;
        }

        @Override
        public void draw() {
            this.mesh.draw();
        }

        @Override
        public void delete() {
            this.mesh.draw();
        }
    }


}
