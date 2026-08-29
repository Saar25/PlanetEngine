// Vertex outputs
in vec2 v_uvCoords;

// Uniforms
uniform sampler2D u_bitmap;
uniform uint u_fontColor;

// Fragment outputs
out vec4 f_color;

// Methods
vec4 getFontColor(void) {
    float r = ((u_fontColor << 0x00) >> 0x18);
    float g = ((u_fontColor << 0x08) >> 0x18);
    float b = ((u_fontColor << 0x10) >> 0x18);
    float a = ((u_fontColor << 0x18) >> 0x18);
    return vec4(r, g, b, a) / 255;
}

// Main
void main(void) {
    f_color = texture(u_bitmap, v_uvCoords).rrrr * getFontColor();
}
