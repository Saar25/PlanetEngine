// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform sampler2D u_contrast;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    vec4 colour = texture(texture, v_position);
    f_colour = (color - .5) * u_contrast + .5;
}
