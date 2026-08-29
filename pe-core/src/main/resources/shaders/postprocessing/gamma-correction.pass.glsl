// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform float u_gamma;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec4 color = texture(u_texture, v_position);
    color.rgb = pow(color.rgb, vec3(1.0 / u_gamma));
    f_color = color;
}
