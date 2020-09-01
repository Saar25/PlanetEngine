#version 400

// Vertex outputs
in vec2 v_uvCoord;

// Uniforms
uniform sampler2D texture;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

void main(void) {
    f_colour = vec4(1);
}
