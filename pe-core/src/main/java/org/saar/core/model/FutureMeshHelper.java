package org.saar.core.model;

import java.util.concurrent.CompletableFuture;

abstract class FutureMeshHelper implements Mesh {

    public abstract FutureMeshHelper next();

    @Override
    public abstract void draw();

    @Override
    public abstract void delete();

    public static FutureMeshHelper create(CompletableFuture<Mesh> task) {
        return new Running(task);
    }

    public static class Running extends FutureMeshHelper {

        private final CompletableFuture<Mesh> task;

        public Running(CompletableFuture<Mesh> task) {
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

    public static class Finished extends FutureMeshHelper {

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
