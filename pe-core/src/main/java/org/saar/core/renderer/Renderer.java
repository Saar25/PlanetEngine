package org.saar.core.renderer;

import org.saar.core.node.Node;

public interface Renderer {

    Node getRenderNode();

    void render();

    void delete();

}
