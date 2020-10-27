package org.saar.core.model.mesh;

import org.saar.core.model.Mesh;

public interface MeshBuilder {

    /**
     * Call this method when done building the mesh
     * This method should load all the data
     */
    void finishBuilding();

    /**
     * Returns the mesh being built by the MeshBuilder
     *
     * @return the mesh
     */
    Mesh asMesh();

}
