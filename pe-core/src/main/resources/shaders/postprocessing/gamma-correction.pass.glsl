// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform float u_gamma;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    vec4 colour = texture(u_texture, v_position);
    colour.rgb = pow(colour.rgb, vec3(1.0 / u_gamma));
    f_colour = colour;
}
