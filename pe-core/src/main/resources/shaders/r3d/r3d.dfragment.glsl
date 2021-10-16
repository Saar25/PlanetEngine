// Vertex outputs
flat in vec3 v_colour;
flat in vec3 v_normal;

// Uniforms
uniform float u_specular;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normalSpecular;

void main(void) {
    f_colour = vec4(v_colour, 1);
    f_normalSpecular = vec4(v_normal, u_specular);
}
