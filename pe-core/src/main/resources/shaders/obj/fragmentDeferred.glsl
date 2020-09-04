#version 400

// Vertex outputs
in vec2 v_uvCoord;
in vec3 v_normal;

// Uniforms
uniform sampler2D texture;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec3 f_normal;

void main(void) {
    f_colour = texture(texture, v_uvCoord);
    f_normal = v_normal;
}
