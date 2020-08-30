package org.saar.core.model;

import org.saar.lwjgl.opengl.drawable.GlDrawable;
import org.saar.lwjgl.opengl.objects.vaos.IVao;

public class ModelBase implements Model {

    private final IVao vao;
    private final GlDrawable drawable;

    public ModelBase(IVao vao, GlDrawable drawable) {
        this.vao = vao;
        this.drawable = drawable;
    }

    @Override
    public final void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        this.drawable.draw();
    }

    @Override
    public final void delete() {
        this.vao.delete();
    }
}
