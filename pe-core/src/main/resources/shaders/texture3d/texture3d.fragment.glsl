// Vertex outputs
flat in vec3 v_color;
in vec2 v_uvCoord;

// Uniforms
uniform sampler2D u_texture;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec4 color = texture(u_texture, v_uvCoord);

    f_color = color;
}
