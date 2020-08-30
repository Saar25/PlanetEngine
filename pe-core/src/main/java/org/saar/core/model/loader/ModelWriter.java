package org.saar.core.model.loader;

import org.saar.core.model.Vertex;
import org.saar.core.node.Node;

public interface ModelWriter<N extends Node, V extends Vertex> {

    void writeInstance(N instance);

    void writeVertex(V vertex);

    void writeIndex(int index);

}
