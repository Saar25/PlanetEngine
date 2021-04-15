package org.saar.gui;

import org.saar.core.node.ParentNode;

public abstract class UIParent extends UIElement implements ParentNode {

    public void process() {
//        getChildren().forEach(Spatial::process);
    }

    public void update() {
        /*for (int i = 0; i < getChildren().size(); i++) {
            getChildren().get(i).update();
        }*/
    }

    public void delete() {
//        getChildren().forEach(Spatial::delete);
    }
}
