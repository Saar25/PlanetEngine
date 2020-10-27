package org.saar.core.model;

import java.util.concurrent.CompletableFuture;

public class FutureMesh implements Mesh {

    private FutureMeshHelper helper;

    public FutureMesh(CompletableFuture<Mesh> task) {
        this.helper = FutureMeshHelper.create(task);
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
