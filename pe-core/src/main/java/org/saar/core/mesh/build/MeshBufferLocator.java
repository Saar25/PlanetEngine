package org.saar.core.mesh.build;

import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshInstanceBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;
import org.saar.core.util.reflection.FieldsLocator;

import java.util.List;

public final class MeshBufferLocator {

    private final FieldsLocator fieldsLocator;

    public MeshBufferLocator(MeshPrototype meshPrototype) {
        this.fieldsLocator = new FieldsLocator(meshPrototype);
    }

    public List<MeshBuffer> getMeshBuffers() {
        return getMeshBuffers(MeshBuffer.class);
    }

    public List<MeshIndexBuffer> getMeshIndexBuffers() {
        return getMeshBuffers(MeshIndexBuffer.class);
    }

    public List<MeshVertexBuffer> getMeshVertexBuffers() {
        return getMeshBuffers(MeshVertexBuffer.class);
    }

    public List<MeshInstanceBuffer> getMeshInstanceBuffers() {
        return getMeshBuffers(MeshInstanceBuffer.class);
    }

    public <T extends MeshBuffer> List<T> getMeshBuffers(Class<T> tClass) {
        return this.fieldsLocator.getFilteredValues(tClass, MeshBufferProperty.class);
    }
}
