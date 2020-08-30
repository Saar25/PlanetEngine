package org.saar.core.node;

public abstract class AbstractNode implements Node {

    private Node parent;

    @Override
    public Node getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

}
