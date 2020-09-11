#version 400

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D colourTexture;
uniform sampler2D normalTexture;
uniform sampler2D depthTexture;

// Fragment outputs
vec4 f_colour;

void main(void) {
    f_colour = texture(colourTexture, v_position);
}
