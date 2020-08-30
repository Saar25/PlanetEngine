package org.saar.core.common.obj;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.maths.utils.Vector2;
import org.saar.maths.utils.Vector3;
import org.saar.utils.file.TextFileLoader;

import java.util.ArrayList;
import java.util.List;

public class ObjLoader {

    private static final String POSITION = "v";
    private static final String UV_COORDINATE = "vt";
    private static final String NORMAL = "vn";

    public ObjMesh load(String path) throws Exception {
        final List<Vector3fc> positions = new ArrayList<>();
        final List<Vector2fc> uvCoordinates = new ArrayList<>();
        final List<Vector3fc> normals = new ArrayList<>();

        final List<String> lines = TextFileLoader.readAllLines(path);

        for (final String line : lines) {
            final String[] segments = line.split("\\s+");
            switch (segments[0]) {
                case ObjLoader.POSITION:
                    positions.add(parse3f(segments));
                    break;
                case ObjLoader.UV_COORDINATE:
                    uvCoordinates.add(parse2f(segments));
                    break;
                case ObjLoader.NORMAL:
                    normals.add(parse3f(segments));
                    break;
                default:
                    break;
            }
        }

        return null;
    }

    private static Vector2fc parse2f(String... segments) {
        final float x = Float.parseFloat(segments[1]);
        final float y = Float.parseFloat(segments[2]);
        return Vector2.of(x, y);
    }

    private static Vector3fc parse3f(String... segments) {
        final float x = Float.parseFloat(segments[1]);
        final float y = Float.parseFloat(segments[2]);
        final float z = Float.parseFloat(segments[3]);
        return Vector3.of(x, y, z);
    }
}
