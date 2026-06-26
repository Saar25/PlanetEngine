// Vertex outputs
flat in vec3 v_color;
flat in vec3 v_normal;

// Uniforms
uniform float u_specular;

// Fragment outputs
layout (location = 0) out vec4 f_color;
layout (location = 1) out vec4 f_normalSpecular;

void main(void) {
    f_color = vec4(v_color, 1);
    f_normalSpecular = vec4(v_normal, u_specular);
}
