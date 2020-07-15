package org.saar.core.node;

import java.util.HashSet;
import java.util.Set;

public class GroupNode extends Node {

    private final Set<Node> children = new HashSet<>();

    public void addChild(Node child) {
        getChildren().add(child);
        child.setParent(this);
    }

    public Set<Node> getChildren() {
        return this.children;
    }
}
