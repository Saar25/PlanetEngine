#version 400

// Vertex outputs
flat in vec3 v_colour;
flat in vec3 v_normal;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    f_colour = vec4(v_colour, 1.0);
}
