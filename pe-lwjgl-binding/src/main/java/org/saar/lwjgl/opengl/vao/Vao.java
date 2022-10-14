package org.saar.lwjgl.opengl.vao;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.utils.GlConfigs;
import org.saar.lwjgl.opengl.vao.linking.LinkingStrategy;
import org.saar.lwjgl.opengl.vbo.ReadOnlyVbo;

public class Vao implements IVao {

    public static final IVao NULL = new Vao(0);

    public static final ReadOnlyVao EMPTY = Vao.create();

    private final int id;

    private boolean deleted = false;

    private Vao(int id) {
        this.id = id;
    }

    public static Vao create() {
        final int id = GL30.glGenVertexArrays();
        return new Vao(id);
    }

    @Override
    public void loadVbo(ReadOnlyVbo buffer, LinkingStrategy linking, IAttribute... attributes) {
        bind();

        buffer.bind();
        linking.link(attributes);
        for (IAttribute attribute : attributes) {
            attribute.enable();
        }

        unbind();
    }

    @Override
    public void bind() {
        if (!GlConfigs.CACHE_STATE || !BoundVao.isBound(this.id)) {
            GL30.glBindVertexArray(this.id);
            BoundVao.set(this.id);
        }
    }

    @Override
    public void unbind() {
        Vao.NULL.bind();
    }

    @Override
    public void delete() {
        if (!GlConfigs.CACHE_STATE || !this.deleted) {
            GL30.glDeleteVertexArrays(this.id);
            this.deleted = true;
        }
    }
}