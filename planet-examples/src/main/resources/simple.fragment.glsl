// Vertex outputs
in vec2 v_position;
in vec3 v_color;

// Fragment outputs
layout (location = 0) out vec4 f_color;

void main(void) {
    f_color = vec4(v_color, 1.0);
}
