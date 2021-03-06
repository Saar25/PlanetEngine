package org.saar.core.common.r2d;

import org.saar.core.node.Model;

public class Model2D implements Model, Node2D {

    private final Mesh2D mesh;

    public Model2D(Mesh2D mesh) {
        this.mesh = mesh;
    }

    @Override
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}
