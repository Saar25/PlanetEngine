package org.saar.core.model.loader;

import org.saar.core.model.Vertex;
import org.saar.core.node.Node;

import java.util.Collection;

public final class ModelWriters {

    private ModelWriters() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    @SafeVarargs
    public static <T extends Vertex> void writeVertices(ModelVertexWriter<T> writer, T... vertices) {
        for (T vertex : vertices) writer.writeVertex(vertex);
    }

    public static <T extends Vertex> void writeVertices(ModelVertexWriter<T> writer, Collection<T> vertices) {
        for (T vertex : vertices) writer.writeVertex(vertex);
    }

    @SafeVarargs
    public static <T extends Node> void writeNodes(ModelNodeWriter<T> writer, T... nodes) {
        for (T node : nodes) writer.writeNode(node);
    }

    public static <T extends Node> void writeNodes(ModelNodeWriter<T> writer, Collection<T> nodes) {
        for (T node : nodes) writer.writeNode(node);
    }

    public static void writeIndices(ModelIndexWriter writer, int... indices) {
        for (int index : indices) writer.writeIndex(index);
    }

    public static void writeIndices(ModelIndexWriter writer, Collection<Integer> indices) {
        for (Integer index : indices) writer.writeIndex(index);
    }

}
