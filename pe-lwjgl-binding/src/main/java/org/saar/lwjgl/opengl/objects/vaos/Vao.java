package org.saar.lwjgl.opengl.objects.vaos;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.ReadOnlyVbo;
import org.saar.lwjgl.opengl.utils.GlConfigs;

import java.util.ArrayList;
import java.util.List;

public class Vao implements IVao {

    public static final ReadOnlyVao NULL = new Vao(0);
    public static final ReadOnlyVao EMPTY = Vao.create();

    private final int id;

    private final List<Attribute> attributes = new ArrayList<>();

    private boolean deleted = false;

    private Vao(int id) {
        this.id = id;
    }

    public static Vao create() {
        final int id = GL30.glGenVertexArrays();
        return new Vao(id);
    }

    @Override
    public void enableAttributes() {
        for (Attribute attribute : this.attributes) {
            attribute.enable();
        }
    }

    @Override
    public void disableAttributes() {
        for (Attribute attribute : this.attributes) {
            attribute.disable();
        }
    }

    @Override
    public void loadVbo(ReadOnlyVbo buffer, Attribute... attributes) {
        bind();
        buffer.bind();
        linkAttributes(attributes);
        unbind();
    }

    /**
     * Link the attributes, assuming that the data in the vbo is stored as [v1data, v2data, v3data...]
     * and not as [[positions], [texture coordinate], [normals], ...]
     *
     * @param attributes the attributes to link
     */
    private void linkAttributes(Attribute... attributes) {
        int offset = 0;
        int stride = Attribute.sumBytes(attributes);
        for (Attribute attribute : attributes) {
            attribute.link(stride, offset);
            offset += attribute.getBytesPerVertex();
            this.attributes.add(attribute);
        }
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