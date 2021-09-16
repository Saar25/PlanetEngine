package org.saar.lwjgl.opengl.texture;

import org.lwjgl.opengl.*;

public enum TextureTarget {

    TEXTURE_1D(GL11.GL_TEXTURE_1D),
    TEXTURE_2D(GL11.GL_TEXTURE_2D),
    TEXTURE_3D(GL12.GL_TEXTURE_3D),
    TEXTURE_CUBE_MAP(GL13.GL_TEXTURE_CUBE_MAP),

    TEXTURE_1D_ARRAY(GL30.GL_TEXTURE_1D_ARRAY),
    TEXTURE_2D_ARRAY(GL30.GL_TEXTURE_2D_ARRAY),
    TEXTURE_CUBE_MAP_ARRAY(GL40.GL_TEXTURE_CUBE_MAP_ARRAY),

    TEXTURE_RECTANGLE(GL31.GL_TEXTURE_RECTANGLE),
    TEXTURE_BUFFER(GL31.GL_TEXTURE_BUFFER),

    TEXTURE_2D_MULTISAMPLE(GL32.GL_TEXTURE_2D_MULTISAMPLE),
    TEXTURE_2D_MULTISAMPLE_ARRAY(GL32.GL_TEXTURE_2D_MULTISAMPLE_ARRAY),

    TEXTURE_CUBE_MAP_POSITIVE_X(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X),
    TEXTURE_CUBE_MAP_NEGATIVE_X(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X),
    TEXTURE_CUBE_MAP_POSITIVE_Y(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y),
    TEXTURE_CUBE_MAP_NEGATIVE_Y(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y),
    TEXTURE_CUBE_MAP_POSITIVE_Z(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z),
    TEXTURE_CUBE_MAP_NEGATIVE_Z(GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z),
    ;

    private final int value;

    TextureTarget(int value) {
        this.value = value;
    }

    public static TextureTarget ofCubeFace(int face) {
        return switch (face) {
            case 0 -> TEXTURE_CUBE_MAP_POSITIVE_X;
            case 1 -> TEXTURE_CUBE_MAP_NEGATIVE_X;
            case 2 -> TEXTURE_CUBE_MAP_POSITIVE_Y;
            case 3 -> TEXTURE_CUBE_MAP_NEGATIVE_Y;
            case 4 -> TEXTURE_CUBE_MAP_POSITIVE_Z;
            case 5 -> TEXTURE_CUBE_MAP_NEGATIVE_Z;
            default -> throw new IllegalArgumentException("Cubes do not have face " + face);
        };
    }

    public int get() {
        return value;
    }
}
