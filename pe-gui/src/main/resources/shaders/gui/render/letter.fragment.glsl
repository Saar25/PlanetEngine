// Vertex outputs
in vec2 v_uvCoords;

// Uniforms
uniform sampler2D u_bitmap;

// Fragment outputs
out vec4 f_colour;

// Main
void main(void) {
    f_colour = texture(u_bitmap, v_uvCoords).rrrr;
}
