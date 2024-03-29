#version 400

// Vertex outputs
in vec2 v_position;
in vec3 v_colour;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    f_colour = vec4(v_colour, 1.0);
}
