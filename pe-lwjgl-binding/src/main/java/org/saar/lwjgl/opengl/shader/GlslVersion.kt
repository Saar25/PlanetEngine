package org.saar.lwjgl.opengl.shader;

public class GlslVersion {

    public enum Type {
        NONE(""),
        CORE("core"),
        COMPATIBILITY("compatibility"),
        ;

        private final String value;

        Type(String value) {
            this.value = value;
        }
    }

    public static final GlslVersion NONE = new GlslVersion(0, 0, Type.NONE);

    public static final GlslVersion V110 = new GlslVersion(1, 1, Type.NONE);
    public static final GlslVersion V120 = new GlslVersion(1, 2, Type.NONE);
    public static final GlslVersion V130 = new GlslVersion(1, 3, Type.NONE);
    public static final GlslVersion V140 = new GlslVersion(1, 4, Type.NONE);
    public static final GlslVersion V150 = new GlslVersion(1, 5, Type.NONE);
    public static final GlslVersion V330 = new GlslVersion(3, 3, Type.NONE);
    public static final GlslVersion V400 = new GlslVersion(4, 0, Type.NONE);
    public static final GlslVersion V410 = new GlslVersion(4, 1, Type.NONE);
    public static final GlslVersion V420 = new GlslVersion(4, 2, Type.NONE);
    public static final GlslVersion V430 = new GlslVersion(4, 3, Type.NONE);
    public static final GlslVersion V440 = new GlslVersion(4, 4, Type.NONE);
    public static final GlslVersion V450 = new GlslVersion(4, 5, Type.NONE);
    public static final GlslVersion V460 = new GlslVersion(4, 6, Type.NONE);

    private final int major;
    private final int minor;
    private final Type type;

    public GlslVersion(int major, int minor, Type type) {
        this.major = major;
        this.minor = minor;
        this.type = type;
    }

    @Override
    public String toString() {
        if (this == NONE) return "";
        final String type = this.type.value;
        final String version = this.major + "" + this.minor + "0";
        return "#version " + version + ' ' + type;
    }
}
