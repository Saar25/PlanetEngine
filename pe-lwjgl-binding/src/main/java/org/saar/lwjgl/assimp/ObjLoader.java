package org.saar.lwjgl.assimp;

import org.saar.lwjgl.opengl.primitive.GlFloat2;
import org.saar.lwjgl.opengl.primitive.GlFloat3;
import org.saar.lwjgl.opengl.primitive.GlInt3;
import org.saar.lwjgl.opengl.primitive.GlUInt;
import org.saar.utils.file.TextFileLoader;

import java.util.ArrayList;
import java.util.List;

public final class ObjLoader {

    private static final String POSITION = "v";
    private static final String UV_COORD = "vt";
    private static final String NORMAL = "vn";
    private static final String FACE = "f";

    private ObjLoader() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static AssimpData load(String path) throws Exception {
        final List<String> lines = TextFileLoader.readAllLines(path);

        final List<GlFloat3> positions = new ArrayList<>();
        final List<GlFloat2> uvCoords = new ArrayList<>();
        final List<GlFloat3> normals = new ArrayList<>();
        final List<GlInt3> faces = new ArrayList<>();

        for (String line : lines) {
            final String[] values = line.split(" ");
            switch (values[0]) {
                case ObjLoader.POSITION:
                    positions.add(GlFloat3.of(
                            Float.parseFloat(values[1]),
                            Float.parseFloat(values[2]),
                            Float.parseFloat(values[3])
                    ));
                    break;
                case ObjLoader.UV_COORD:
                    uvCoords.add(GlFloat2.of(
                            Float.parseFloat(values[1]),
                            Float.parseFloat(values[2])
                    ));
                    break;
                case ObjLoader.NORMAL:
                    normals.add(GlFloat3.of(
                            Float.parseFloat(values[1]),
                            Float.parseFloat(values[2]),
                            Float.parseFloat(values[3])
                    ));
                    break;
                case ObjLoader.FACE:
                    for (int i = 1; i < 4; i++) {
                        final String[] face = values[i].split("/");
                        faces.add(GlInt3.of(
                                Integer.parseInt(face[0]),
                                Integer.parseInt(face[1]),
                                Integer.parseInt(face[2])
                        ));
                    }
                    break;
            }
        }

        return ObjLoader.processFaces(positions, uvCoords, normals, faces);
    }

    private static AssimpData processFaces(List<GlFloat3> filePositions,
                                           List<GlFloat2> fileUvCoords,
                                           List<GlFloat3> fileNormals,
                                           List<GlInt3> fileFaces) {
        final List<GlFloat3> positions = new ArrayList<>();
        final List<GlFloat2> uvCoords = new ArrayList<>();
        final List<GlFloat3> normals = new ArrayList<>();
        final List<GlUInt> indices = new ArrayList<>();
        for (int i = 0; i < fileFaces.size(); i++) {
            final GlInt3 fileFace = fileFaces.get(i);
            positions.add(filePositions.get(fileFace.getValue().x() - 1));
            uvCoords.add(fileUvCoords.get(fileFace.getValue().y() - 1));
            normals.add(fileNormals.get(fileFace.getValue().z() - 1));
            indices.add(GlUInt.of(i));
        }
        final AssimpData mesh = new AssimpData();
        mesh.loadPositions(positions.toArray(new GlFloat3[0]));
        mesh.loadUvCoords(uvCoords.toArray(new GlFloat2[0]));
        mesh.loadNormals(normals.toArray(new GlFloat3[0]));
        mesh.loadIndices(indices.toArray(new GlUInt[0]));
        return mesh;
    }
}
