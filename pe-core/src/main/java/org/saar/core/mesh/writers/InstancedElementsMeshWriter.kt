package org.saar.core.mesh.writers

import org.saar.core.mesh.Instance
import org.saar.core.mesh.Vertex

interface InstancedElementsMeshWriter<V : Vertex, I : Instance> :
    MeshVertexWriter<V>, MeshIndexWriter, MeshInstanceWriter<I>