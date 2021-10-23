package org.saar.core.mesh.writers

import org.saar.core.mesh.Instance
import org.saar.core.mesh.Vertex

interface InstancedArraysMeshWriter<V : Vertex, I : Instance> : MeshVertexWriter<V>, MeshInstanceWriter<I>