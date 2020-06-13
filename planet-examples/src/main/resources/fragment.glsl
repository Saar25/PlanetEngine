#version 400

// Vertex outputs
in vec2 v_position;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    f_colour = vec4((v_position + .5), 0.5, 1.0);
}
