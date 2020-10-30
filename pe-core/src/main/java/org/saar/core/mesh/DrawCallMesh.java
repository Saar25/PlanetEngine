package org.saar.core.mesh;

import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.objects.vaos.IVao;

public class DrawCallMesh implements Mesh {

    private final IVao vao;
    private final DrawCall drawCall;

    public DrawCallMesh(IVao vao, DrawCall drawCall) {
        this.vao = vao;
        this.drawCall = drawCall;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        this.drawCall.doDrawCall();
    }

    @Override
    public void delete() {
        this.vao.delete();
    }
}
