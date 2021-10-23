package org.saar.core.mesh.writers

import org.saar.core.mesh.Vertex

interface ElementsMeshWriter<V : Vertex> : MeshVertexWriter<V>, MeshIndexWriter