package org.saar.core.model.mesh;

import org.saar.core.model.mesh.buffers.MeshIndexBuffer;
import org.saar.core.model.mesh.buffers.MeshInstanceBuffer;
import org.saar.core.model.mesh.buffers.MeshVertexBuffer;
import org.saar.utils.reflection.FieldsLocator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public final class MeshBufferLocator {

    private final FieldsLocator fieldsLocator;

    public MeshBufferLocator(MeshPrototype object) {
        this.fieldsLocator = new FieldsLocator(object);
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
        final List<Field> fields = this.fieldsLocator
                .getAnnotatedFields(MeshBufferProperty.class);
        return this.fieldsLocator.getValues(fields).stream()
                .filter(tClass::isInstance).map(tClass::cast)
                .collect(Collectors.toList());
    }
}
