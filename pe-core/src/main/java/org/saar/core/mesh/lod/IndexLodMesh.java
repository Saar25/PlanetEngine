package org.saar.core.mesh.lod;

import org.saar.core.mesh.Mesh;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.vaos.IVao;

import java.util.ArrayList;
import java.util.List;

public class IndexLodMesh implements Mesh {

    private final List<DrawCall> drawCalls = new ArrayList<>();
    private LevelOfDetail lod = new LevelOfDetail(0, 0);

    private final IVao vao;

    public IndexLodMesh(IVao vao) {
        this.vao = vao;
    }

    private void updateLod() {
        final int size = this.drawCalls.size();
        this.lod = new LevelOfDetail(0, size);
    }

    public void addMesh(DrawCall drawCall) {
        this.drawCalls.add(drawCall);
    }

    public void removeMesh(DrawCall drawCall) {
        this.drawCalls.remove(drawCall);
    }

    public LevelOfDetail getLod() {
        return this.lod;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        final int index = getLod().get();
        this.drawCalls.get(index).doDrawCall();
    }

    @Override
    public void delete() {
        this.vao.delete();
    }
}
