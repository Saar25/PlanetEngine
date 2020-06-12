package org.saar.utils.node;

import engine.rendering.Spatial;

import java.util.List;

public interface IParent {

    List<? extends Spatial> getChildren();

}
