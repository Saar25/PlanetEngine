package org.saar.core.common.terrain.smooth;

import org.joml.Vector2ic;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.core.common.smooth.Smooth;
import org.saar.core.common.smooth.SmoothMeshWriter;
import org.saar.core.common.smooth.SmoothVertex;
import org.saar.core.common.terrain.colour.ColourGenerator;
import org.saar.core.common.terrain.height.HeightGenerator;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.maths.utils.Maths;
import org.saar.maths.utils.Vector3;

public class MeshGenerator {

    private static final Vector3f v1 = Vector3.create();
    private static final Vector3f v2 = Vector3.create();
    private static final Vector3f v3 = Vector3.create();

    private static final Attribute[] ATTRIBUTES = {
            Attribute.of(0, 3, DataType.FLOAT, false), // position
            Attribute.of(1, 3, DataType.FLOAT, false), // colour
            Attribute.of(2, 3, DataType.FLOAT, false), // normal
    };

    private final int vertices;
    private final float space;
    private final HeightGenerator heightGenerator;
    private final ColourGenerator colourGenerator;
    private final Vector2ic position;

    public MeshGenerator(int vertices, HeightGenerator heightGenerator, ColourGenerator colourGenerator, Vector2ic position) {
        this.vertices = vertices;
        this.heightGenerator = heightGenerator;
        this.colourGenerator = colourGenerator;
        this.position = position;
        this.space = 1f / this.vertices;
    }

    public int getTotalVertexCount() {
        final int outerVertices = this.vertices * this.vertices;
        final int centerVertices = (this.vertices - 1) * (this.vertices - 1);
        return centerVertices + outerVertices;
    }

    public int getTotalIndexCount() {
        return (this.vertices - 1) * (this.vertices - 1) * 4 * 3;
    }

    public void generateVertices(SmoothMeshWriter writer) {
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

    private Vector3f vertexPosition(float x, float z, Vector3f dest) {
        final float y = this.heightGenerator.generateHeight(
                this.position.x() + x, this.position.y() + z);
        return dest.set(x, y, z);
    }

    private Vector3f vertexPosition(float x, float z) {
        return vertexPosition(x, z, v1);
    }

    private Vector3f vertexNormal(Vector3fc position) {
        final Vector3f p2 = vertexPosition(position.x(), position.z() - this.space, v2);
        final Vector3f p3 = vertexPosition(position.x() - this.space, position.z(), v3);
        return Maths.calculateNormal(position, p2, p3);
    }

    private Vector3fc vertexColour(Vector3fc v) {
        final float tx = this.position.x() + v.x();
        final float tz = this.position.y() + v.z();
        return this.colourGenerator.generateColour(tx, v.y(), tz);
    }

    private SmoothVertex generateVertex(float x, float z) {
        final Vector3fc position = Vector3.of(vertexPosition(x, z));
        final Vector3fc normal = Vector3.of(vertexNormal(position));
        final Vector3fc colour = Vector3.of(vertexColour(position));
        final Vector3fc target = Vector3.of(
                (float) Math.random() * .01f,
                (float) Math.random() * 0.1f,
                (float) Math.random() * .01f);
        return Smooth.vertex(position, normal, colour, target);
    }

    public void generateIndices(SmoothMeshWriter writer) {
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

}
