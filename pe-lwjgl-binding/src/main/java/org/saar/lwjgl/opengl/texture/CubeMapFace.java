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
        switch (face) {
            case 0:
                return CubeMapFace.POSITIVE_X;
            case 1:
                return CubeMapFace.NEGATIVE_X;
            case 2:
                return CubeMapFace.POSITIVE_Y;
            case 3:
                return CubeMapFace.NEGATIVE_Y;
            case 4:
                return CubeMapFace.POSITIVE_Z;
            case 5:
                return CubeMapFace.NEGATIVE_Z;
        }
        throw new IllegalArgumentException("Cubes do not have face " + face);
    }

    public int get() {
        return value;
    }
}
