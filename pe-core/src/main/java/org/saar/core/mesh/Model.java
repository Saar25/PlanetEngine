package org.saar.core.mesh;

public interface Model {

    Mesh getMesh();

    default void draw() {
        getMesh().draw();
    }

    default void delete() {
        getMesh().delete();
    }
}
