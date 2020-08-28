package org.saar.lwjgl.opengl.objects;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.utils.GlConfigs;

import java.util.ArrayList;
import java.util.List;

public class Vao {

    private static final Vao NULL = new Vao(0);
    private static final Vao FIRST = Vao.create();

    private static int boundVao = 0;

    private final int id;

    private final List<IVbo> buffers = new ArrayList<>();
    private final List<Attribute> attributes = new ArrayList<>();

    private boolean deleted = false;

    private Vao(int id) {
        this.id = id;
    }

    public static Vao create() {
        final int id = GL30.glGenVertexArrays();
        return new Vao(id);
    }

    /**
     * Enables the vao attributes
     */
    public void enableAttributes() {
        for (Attribute attribute : this.attributes) {
            attribute.enable();
        }
    }

    /**
     * Disables the vao attributes
     */
    public void disableAttributes() {
        for (Attribute attribute : this.attributes) {
            attribute.disable();
        }
    }

    /**
     * Loads a vbo and linking the given attributes to it
     *
     * @param vbo        the vbo to load
     * @param attributes the attributes
     */
    public void loadVbo(IVbo vbo, Attribute... attributes) {
        this.bind();
        vbo.bind();
        linkAttributes(attributes);
        this.buffers.add(vbo);
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

    /**
     * Returns whether the vao is bound
     *
     * @return true if bound false otherwise
     */
    private boolean isBound() {
        return Vao.boundVao == this.id;
    }

    /**
     * Binds the vao
     */
    public void bind() {
        if (GlConfigs.CACHE_STATE || !isBound()) {
            GL30.glBindVertexArray(this.id);
            Vao.boundVao = this.id;
        }
    }

    /**
     * Unbind the vao
     */
    public void unbind() {
        Vao.NULL.bind();
    }

    /**
     * Delete this vao and its related buffers
     */
    public void delete() {
        this.buffers.forEach(IVbo::delete);
        if (GlConfigs.CACHE_STATE || !this.deleted) {
            GL30.glDeleteVertexArrays(this.id);
            this.deleted = true;
        }
    }
}