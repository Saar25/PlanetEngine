package org.saar.core.model.data;

import org.saar.core.node.Node;

public interface NodeWriter<T extends Node> {

    void write(T node);

}
