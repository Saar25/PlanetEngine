package org.saar.core.common.r2d;

import org.saar.core.mesh.Model;

public class Model2D implements Model {

    private final Mesh2D mesh;

    public Model2D(Mesh2D mesh) {
        this.mesh = mesh;
    }

    @Override
    public Mesh2D getMesh() {
        return this.mesh;
    }
}
