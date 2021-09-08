// Vertex outputs
in vec2 v_uvCoords;

// Uniforms
uniform sampler2D u_bitmap;
uniform uint u_fontColour;

// Fragment outputs
out vec4 f_colour;

// Methods
vec4 getFontColour(void) {
    float r = ((u_fontColour << 0x00) >> 0x18);
    float g = ((u_fontColour << 0x08) >> 0x18);
    float b = ((u_fontColour << 0x10) >> 0x18);
    float a = ((u_fontColour << 0x18) >> 0x18);
    return vec4(r, g, b, a) / 255;
}

// Main
void main(void) {
    f_colour = texture(u_bitmap, v_uvCoords).rrrr * getFontColour();
}
