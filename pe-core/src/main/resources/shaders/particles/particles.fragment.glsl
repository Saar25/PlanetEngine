// Vertex outputs
in vec2 v_uvCoords;

// Uniforms
uniform sampler2D u_texture;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    f_color = texture(u_texture, v_uvCoords);
}
