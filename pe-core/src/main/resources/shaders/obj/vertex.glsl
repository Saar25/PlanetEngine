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
out vec3 v_normal;

vec4 calculatePosition() {
    return transformationMatrix * vec4(in_position, 1.0);
}

vec3 calculateNormal() {
    return normalize((transformationMatrix * vec4(in_normal, 0.0)).xyz);
}

void main(void) {
    v_uvCoord = in_uvCoord;

    v_normal = calculateNormal();

    vec4 world = calculatePosition();
    gl_Position = viewProjectionMatrix * world;
}
