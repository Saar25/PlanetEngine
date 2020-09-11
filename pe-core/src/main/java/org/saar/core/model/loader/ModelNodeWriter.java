package org.saar.core.model.loader;

import org.saar.core.node.Node;

public interface ModelNodeWriter<T extends Node> {

    void writeNode(T node);

}
