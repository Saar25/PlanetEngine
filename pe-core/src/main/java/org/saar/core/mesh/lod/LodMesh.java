package org.saar.core.mesh.lod;

import org.saar.core.mesh.Mesh;

public class LodMesh implements Mesh {

    private LodMeshHelper helper = LodMeshHelper.empty();

    public void addMesh(Mesh mesh) {
        this.helper = this.helper.addMesh(mesh);
    }

    public void removeMesh(Mesh mesh) {
        this.helper = this.helper.removeMesh(mesh);
    }

    public LevelOfDetail getLod() {
        return this.helper.getLod();
    }

    @Override
    public void draw() {
        this.helper.draw();
    }

    @Override
    public void delete() {
        this.helper.delete();
    }
}
