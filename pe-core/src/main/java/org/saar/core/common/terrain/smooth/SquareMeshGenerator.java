package org.saar.core.common.terrain.smooth;

import org.joml.Vector2ic;
import org.joml.Vector3f;
import org.saar.core.common.smooth.Smooth;
import org.saar.core.common.smooth.SmoothVertex;
import org.saar.core.common.terrain.colour.ColourGenerator;
import org.saar.core.common.terrain.height.HeightGenerator;
import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;
import org.saar.maths.utils.Vector3;

public class SquareMeshGenerator extends MeshGeneratorBase {

    private final Vector3f vPosition = Vector3.create();
    private final Vector3f vNormal = Vector3.create();
    private final Vector3f vColour = Vector3.create();
    private final Vector3f vTarget = Vector3.create();
    private final SmoothVertex vertex = Smooth.vertex(this.vPosition, this.vNormal, this.vColour, this.vTarget);

    private final int vertices;
    private final float space;
    private final HeightGenerator heightGenerator;
    private final ColourGenerator colourGenerator;
    private final Vector2ic position;

    public SquareMeshGenerator(int vertices, HeightGenerator heightGenerator,
                               ColourGenerator colourGenerator, Vector2ic position) {
        this.vertices = vertices;
        this.heightGenerator = heightGenerator;
        this.colourGenerator = colourGenerator;
        this.position = position;
        this.space = 1f / (this.vertices - 1);
    }

    @Override
    public int getTotalVertexCount() {
        return this.vertices * this.vertices;
    }

    @Override
    public int getTotalIndexCount() {
        return (this.vertices - 1) * (this.vertices - 1) * 6;
    }

    @Override
    public void generateVertices(MeshVertexWriter<SmoothVertex> writer) {
        for (int x = 0; x < this.vertices; x++) {
            final float vx = x * this.space - .5f;
            for (int z = 0; z < this.vertices; z++) {
                final float vz = z * this.space - .5f;
                writer.writeVertex(generateVertex(vx, vz));
            }
        }
    }

    @Override
    public void generateIndices(MeshIndexWriter writer) {
        for (int x0 = 0; x0 < this.vertices - 1; x0++) {
            for (int z0 = 0; z0 < this.vertices - 1; z0++) {
                final int x1 = x0 + 1;
                final int z1 = z0 + 1;
                final int[] current = {
                        x0 + z0 * this.vertices,
                        x1 + z0 * this.vertices,
                        x0 + z1 * this.vertices,
                        x1 + z1 * this.vertices,
                };

                writer.writeIndex(current[2]);
                writer.writeIndex(current[0]);
                writer.writeIndex(current[1]);

                writer.writeIndex(current[2]);
                writer.writeIndex(current[1]);
                writer.writeIndex(current[3]);
            }
        }
    }

    @Override
    protected HeightGenerator getHeightGenerator() {
        return this.heightGenerator;
    }

    @Override
    protected ColourGenerator getColourGenerator() {
        return this.colourGenerator;
    }

    @Override
    protected float xPosition() {
        return this.position.x();
    }

    @Override
    protected float zPosition() {
        return this.position.y();
    }

    private SmoothVertex generateVertex(float x, float z) {
        this.vPosition.set(vertexPosition(x, z));
        this.vNormal.set(vertexNormal(this.vPosition, -this.space, -this.space));
        this.vColour.set(vertexColour(this.vPosition));
        this.vTarget.set(0, Math.random() * 0.1f, 0);
        return this.vertex;
    }

}
