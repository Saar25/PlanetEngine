out vec4 fragColor;

uniform uint color;

vec4 getColor() {
    float r = ((color << 0 ) >> 24);
    float g = ((color << 8 ) >> 24);
    float b = ((color << 16) >> 24);
    float a = ((color << 24) >> 24);
    return vec4(r, g, b, a) / 255;
}

void main(void) {
    fragColor = getColor();

}
