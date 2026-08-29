// Vertex outputs
flat in vec3 v_color;
flat in vec3 v_normal;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    f_color = vec4(v_color, 1.0);
}
