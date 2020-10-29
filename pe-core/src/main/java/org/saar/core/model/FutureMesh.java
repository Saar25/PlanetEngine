package org.saar.core.model;

import java.util.concurrent.CompletableFuture;

public class FutureMesh implements Mesh {

    private FutureMeshHelper helper;

    private FutureMesh(FutureMeshHelper helper) {
        this.helper = helper;
    }

    public static FutureMesh create(CompletableFuture<? extends Mesh> task) {
        return new FutureMesh(FutureMeshHelper.create(task));
    }

    public static FutureMesh unloaded(CompletableFuture<? extends UnloadedMesh> task) {
        return new FutureMesh(FutureMeshHelper.unloaded(task));
    }

    @Override
    public void draw() {
        this.helper = this.helper.next();
        this.helper.draw();
    }

    @Override
    public void delete() {
        this.helper = this.helper.next();
        this.helper.delete();
    }
}
