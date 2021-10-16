package org.saar.lwjgl.opengl.texture;

import org.lwjgl.opengl.GL13;

public enum CubeMapFace {

    POSITIVE_X(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X),
    NEGATIVE_X(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X),
    POSITIVE_Y(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y),
    NEGATIVE_Y(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y),
    POSITIVE_Z(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z),
    NEGATIVE_Z(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z),
    ;

    private final int value;

    CubeMapFace(int value) {
        this.value = value;
    }

    public static CubeMapFace ofCubeFace(int face) {
        return switch (face) {
            case 0 -> CubeMapFace.POSITIVE_X;
            case 1 -> CubeMapFace.NEGATIVE_X;
            case 2 -> CubeMapFace.POSITIVE_Y;
            case 3 -> CubeMapFace.NEGATIVE_Y;
            case 4 -> CubeMapFace.POSITIVE_Z;
            case 5 -> CubeMapFace.NEGATIVE_Z;
            default -> throw new IllegalArgumentException("Cubes do not have face " + face);
        };
    }

    public int get() {
        return value;
    }
}
