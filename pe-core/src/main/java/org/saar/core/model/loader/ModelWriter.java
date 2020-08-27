package org.saar.core.model.loader;

import org.saar.core.model.Vertex;
import org.saar.core.node.Node;

public interface ModelWriter<N extends Node, V extends Vertex> extends NodeWriter<N>, VertexWriter<V> {

}
