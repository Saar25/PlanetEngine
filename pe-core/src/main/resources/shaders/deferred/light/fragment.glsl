#version 400

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D colourTexture;
uniform sampler2D normalTexture;
uniform sampler2D depthTexture;

// Fragment outputs
out vec4 f_colour;

void main(void) {
    colourTexture;
    normalTexture;
    depthTexture;
    f_colour = texture(normalTexture, v_position);
}
