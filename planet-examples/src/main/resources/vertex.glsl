#version 400

// Per Vertex attibutes
layout (location = 0) in vec2 in_position;

// Vertex outputs
out vec2 v_position;

void main(void) {
    v_position = in_position;

    gl_Position = vec4(in_position, 0.0, 1.0);
}
