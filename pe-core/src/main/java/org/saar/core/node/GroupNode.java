package org.saar.core.node;

import java.util.ArrayList;
import java.util.List;

public class GroupNode<T extends Node> extends AbstractNode implements Node {

    private final List<T> children = new ArrayList<>();

    public void addChild(T child) {
        getChildren().add(child);
        child.setParent(this);
    }

    public List<T> getChildren() {
        return this.children;
    }
}
