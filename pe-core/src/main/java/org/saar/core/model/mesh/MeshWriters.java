package org.saar.core.model.mesh;

import org.saar.core.model.Vertex;
import org.saar.core.model.mesh.writers.MeshIndexWriter;
import org.saar.core.model.mesh.writers.MeshNodeWriter;
import org.saar.core.model.mesh.writers.MeshVertexWriter;
import org.saar.core.node.Node;

import java.util.Collection;

public final class MeshWriters {

    private MeshWriters() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    @SafeVarargs
    public static <T extends Vertex> void writeVertices(MeshVertexWriter<T> writer, T... vertices) {
        for (T vertex : vertices) writer.writeVertex(vertex);
    }

    public static <T extends Vertex> void writeVertices(MeshVertexWriter<T> writer, Collection<T> vertices) {
        for (T vertex : vertices) writer.writeVertex(vertex);
    }

    @SafeVarargs
    public static <T extends Node> void writeNodes(MeshNodeWriter<T> writer, T... nodes) {
        for (T node : nodes) writer.writeNode(node);
    }

    public static <T extends Node> void writeNodes(MeshNodeWriter<T> writer, Collection<T> nodes) {
        for (T node : nodes) writer.writeNode(node);
    }

    public static void writeIndices(MeshIndexWriter writer, int... indices) {
        for (int index : indices) writer.writeIndex(index);
    }

    public static void writeIndices(MeshIndexWriter writer, Collection<Integer> indices) {
        for (Integer index : indices) writer.writeIndex(index);
    }

}
