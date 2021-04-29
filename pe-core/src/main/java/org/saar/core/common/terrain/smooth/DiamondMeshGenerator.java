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

public class DiamondMeshGenerator extends MeshGeneratorBase {

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

    public DiamondMeshGenerator(int vertices, HeightGenerator heightGenerator, ColourGenerator colourGenerator, Vector2ic position) {
        this.vertices = vertices;
        this.heightGenerator = heightGenerator;
        this.colourGenerator = colourGenerator;
        this.position = position;
        this.space = 1f / (this.vertices - 1);
    }

    @Override
    public int getTotalVertexCount() {
        final int outerVertices = this.vertices * this.vertices;
        final int centerVertices = (this.vertices - 1) * (this.vertices - 1);
        return centerVertices + outerVertices;
    }

    @Override
    public int getTotalIndexCount() {
        return (this.vertices - 1) * (this.vertices - 1) * 4 * 3;
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
        for (int x = 0; x < this.vertices - 1; x++) {
            final float vx = (x + .5f) * this.space - .5f;
            for (int z = 0; z < this.vertices - 1; z++) {
                final float vz = (z + .5f) * this.space - .5f;
                writer.writeVertex(generateVertex(vx, vz));
            }
        }
    }

    @Override
    public void generateIndices(MeshIndexWriter writer) {
        final int innerOffset = this.vertices * this.vertices;

        for (int x1 = 0; x1 < this.vertices - 1; x1++) {
            for (int z1 = 0; z1 < this.vertices - 1; z1++) {
                final int x2 = x1 + Math.min(this.vertices - x1 - 1, 1);
                final int z2 = z1 + Math.min(this.vertices - z1 - 1, 1);
                final int[] current = {
                        x1 + z1 * this.vertices,
                        x2 + z1 * this.vertices,
                        x2 + z2 * this.vertices,
                        x1 + z2 * this.vertices
                };
                final int center = innerOffset + x1 + z1 * (this.vertices - 1);

                for (int i = 0; i < 4; i++) {
                    writer.writeIndex(current[(i + 1) % 4]);
                    writer.writeIndex(center);
                    writer.writeIndex(current[i]);
                }
            }
        }
    }

    @Override
    public HeightGenerator getHeightGenerator() {
        return this.heightGenerator;
    }

    @Override
    public ColourGenerator getColourGenerator() {
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
