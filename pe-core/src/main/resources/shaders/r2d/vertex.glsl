#version 400

// Per Vertex attibutes
layout (location = 0) in vec2 in_position;
layout (location = 1) in vec3 in_colour;

// Vertex outputs
out vec2 v_position;
out vec3 v_colour;

void main(void) {
    v_position = in_position;
    v_colour = in_colour;

    gl_Position = vec4(in_position, 0.0, 1.0);
}
