// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture1;
uniform sampler2D u_texture2;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec4 texture1 = texture(u_texture1, v_position);
    vec4 texture2 = texture(u_texture2, v_position);
    f_color = texture1 + texture2;
}
