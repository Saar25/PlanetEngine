#version 400

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_uvCoord;
layout (location = 2) in vec3 in_normal;

// Uniforms
uniform mat4 viewProjectionMatrix;
uniform mat4 transformationMatrix;

// Vertex outputs
out vec2 v_uvCoord;

void main(void) {
    v_uvCoord = in_uvCoord;

    vec4 world = transformationMatrix * vec4(in_position, 1.0);
    gl_Position = viewProjectionMatrix * world;
}
