package org.saar.core.node;

import java.util.List;

public interface ParentNode extends Node {

    List<ChildNode> getChildren();

}
