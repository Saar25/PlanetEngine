package org.saar.core.node;

import java.util.HashSet;
import java.util.Set;

public class GroupNode<T extends Node> extends AbstractNode implements Node {

    private final Set<T> children = new HashSet<>();

    public void addChild(T child) {
        getChildren().add(child);
        child.setParent(this);
    }

    public Set<T> getChildren() {
        return this.children;
    }
}
