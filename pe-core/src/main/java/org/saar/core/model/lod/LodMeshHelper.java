package org.saar.core.model.lod;

import org.saar.core.model.Mesh;

import java.util.ArrayList;
import java.util.List;

public abstract class LodMeshHelper {

    public abstract LevelOfDetail getLod();

    public abstract LodMeshHelper addMesh(Mesh mesh);

    public abstract LodMeshHelper removeMesh(Mesh mesh);

    public abstract void draw();

    public abstract void delete();

    public static LodMeshHelper empty() {
        return Empty.empty;
    }

    private static class Empty extends LodMeshHelper {

        private static final Empty empty = new Empty();

        private final LevelOfDetail lod = new LevelOfDetail(0, 0);

        @Override
        public LevelOfDetail getLod() {
            return this.lod;
        }

        @Override
        public LodMeshHelper addMesh(Mesh mesh) {
            return new Generic(mesh);
        }

        @Override
        public LodMeshHelper removeMesh(Mesh mesh) {
            return this;
        }

        @Override
        public void draw() {
        }

        @Override
        public void delete() {
        }
    }

    private static class Generic extends LodMeshHelper {

        private final List<Mesh> meshes = new ArrayList<>();
        private LevelOfDetail lod = new LevelOfDetail(0, 0);

        public Generic(Mesh mesh) {
            this.meshes.add(mesh);
        }

        private void updateLod() {
            this.lod = new LevelOfDetail(0, this.meshes.size());
        }

        @Override
        public LevelOfDetail getLod() {
            return this.lod;
        }

        @Override
        public LodMeshHelper addMesh(Mesh mesh) {
            this.meshes.add(mesh);
            updateLod();
            return this;
        }

        @Override
        public LodMeshHelper removeMesh(Mesh mesh) {
            this.meshes.remove(mesh);
            updateLod();
            return this;
        }

        @Override
        public void draw() {
            final int lod = getLod().get();
            this.meshes.get(lod).draw();
        }

        @Override
        public void delete() {
            for (Mesh mesh : this.meshes) {
                mesh.delete();
            }
        }
    }


}