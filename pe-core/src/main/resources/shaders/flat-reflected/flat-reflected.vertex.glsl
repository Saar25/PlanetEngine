/**
*
* Flat reflected vertex shader
*
**/

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_uvCoords;

// Uniforms
uniform mat4 u_mvpMatrix;
uniform vec3 u_normal;

// Vertex outputs

flat out vec3 v_normal;
out vec2 v_uvCoords;

void main(void) {
    v_normal = u_normal;

    v_uvCoords = in_uvCoords;

    vec4 world = vec4(in_position, 1.0);
    gl_Position = u_mvpMatrix * world;
}
