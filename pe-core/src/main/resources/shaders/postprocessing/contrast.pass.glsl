// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform float u_contrast;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    vec4 color = texture(u_texture, v_position);
    f_color = (color - .5) * u_contrast + .5;
}
