package org.saar.core.common.terrain.smooth;

import org.joml.Vector2ic;
import org.joml.Vector3f;
import org.saar.core.common.smooth.Smooth;
import org.saar.core.common.smooth.SmoothMeshWriter;
import org.saar.core.common.smooth.SmoothVertex;
import org.saar.core.common.terrain.colour.ColourGenerator;
import org.saar.core.common.terrain.height.HeightGenerator;
import org.saar.maths.utils.Vector3;

public class TriangleMeshGenerator extends MeshGeneratorBase {

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

    public TriangleMeshGenerator(int vertices, HeightGenerator heightGenerator,
                                 ColourGenerator colourGenerator, Vector2ic position) {
        this.vertices = vertices;
        this.heightGenerator = heightGenerator;
        this.colourGenerator = colourGenerator;
        this.position = position;
        this.space = 1f / (this.vertices - 1);
    }

    @Override
    public int getTotalVertexCount() {
        return this.vertices * (2 * this.vertices - 2);
    }

    @Override
    public int getTotalIndexCount() {
        return (this.vertices - 1) * (this.vertices - 1) * 6;
    }

    @Override
    public void generateVertices(SmoothMeshWriter writer) {
        for (int x = 0; x < this.vertices; x++) {
            final float vx = x * this.space - .5f;
            for (int z = 0; z < this.vertices; z++) {
                final float vz = z * this.space - .5f;
                writer.writeVertex(generateVertex(vx, vz));
            }
        }
    }

    @Override
    public void generateIndices(SmoothMeshWriter writer) {
        for (int x = 0; x < this.vertices - 1; x++) {
            for (int z = 0; z < this.vertices - 1; z++) {
                final int x1 = x + 1;
                final int z1 = z + 1;
                final int[] current = {
                        x + z * this.vertices,
                        x1 + z * this.vertices,
                        x + z1 * this.vertices,
                        x1 + z1 * this.vertices,
                };

                if ((x + z) % 2 == 1) {
                    writer.writeIndex(current[0]);
                    writer.writeIndex(current[1]);
                    writer.writeIndex(current[2]);

                    writer.writeIndex(current[1]);
                    writer.writeIndex(current[3]);
                    writer.writeIndex(current[2]);
                } else {
                    writer.writeIndex(current[0]);
                    writer.writeIndex(current[3]);
                    writer.writeIndex(current[2]);

                    writer.writeIndex(current[1]);
                    writer.writeIndex(current[3]);
                    writer.writeIndex(current[0]);
                }
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
