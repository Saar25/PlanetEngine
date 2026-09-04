// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    vec4 colour = texture(u_texture, v_position);
    colour.rgb *= vec3(.5, .5, 2);

    f_colour = colour;
}
