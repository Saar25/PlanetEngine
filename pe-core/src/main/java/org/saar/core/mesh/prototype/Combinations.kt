package org.saar.core.mesh.prototype

import org.saar.core.mesh.Instance
import org.saar.core.mesh.Vertex

interface IndexedVertexMeshPrototype<V : Vertex> :
    VertexMeshPrototype<V>,
    IndexedMeshPrototype

interface InstancedVertexMeshPrototype<V : Vertex, I : Instance> :
    VertexMeshPrototype<V>,
    InstancedMeshPrototype<I>

interface InstancedIndexedMeshPrototype<I : Instance> :
    InstancedMeshPrototype<I>,
    IndexedMeshPrototype

interface InstancedIndexedVertexMeshPrototype<V : Vertex, I : Instance> :
    VertexMeshPrototype<V>,
    InstancedMeshPrototype<I>,
    IndexedMeshPrototype