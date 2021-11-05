package org.saar.core.common.particles;

import org.saar.core.mesh.writers.InstancedArraysMeshWriter;

public class ParticlesMeshWriter implements InstancedArraysMeshWriter<ParticlesVertex, ParticlesInstance> {

    private final ParticlesMeshPrototype prototype;

    public ParticlesMeshWriter(ParticlesMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(ParticlesVertex vertex) {
    }

    @Override
    public void writeInstance(ParticlesInstance instance) {
        this.prototype.getPositionBuffer().getWriter().write3f(instance.getPosition3f());
        this.prototype.getBirthBuffer().getWriter().writeInt(instance.getBirth());
    }

}
