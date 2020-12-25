/**
*
* Flat reflected vertex shader
*
**/
const vec2 uvCoords[] = {
    vec2(0, 0), vec2(0, 1),
    vec2(1, 1), vec2(1, 0)
};

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;

// Uniforms
uniform mat4 u_mvpMatrix;
uniform vec3 u_normal;

// Vertex outputs

flat out vec3 v_normal;
out vec2 v_uvCoords;
out vec4 v_clipSpace;
out vec2 cors;

void main(void) {
    v_normal = u_normal;

    cors = uvCoords[gl_VertexID];

    vec4 world = vec4(in_position, 1.0);
    v_clipSpace = u_mvpMatrix * world;
    gl_Position = v_clipSpace;
}
