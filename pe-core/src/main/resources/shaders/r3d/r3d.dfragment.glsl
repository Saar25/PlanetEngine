// Vertex outputs
flat in vec3 v_colour;
flat in vec3 v_normal;

// Uniforms
uniform float u_specular;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normal;
layout (location = 2) out vec4 f_specular;

void main(void) {
    f_colour = vec4(v_colour, 1);
    f_normal = vec4(v_normal, 1);
    f_specular = vec4(u_specular, 1, 1, 1);
}
