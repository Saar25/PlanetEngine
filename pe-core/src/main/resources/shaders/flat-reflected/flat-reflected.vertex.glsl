/**
*
* Flat reflected vertex shader
*
**/
const vec2 uvCoords[] = vec2[](
    vec2(0, 0), vec2(0, 1),
    vec2(1, 1), vec2(1, 0)
);

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;

// Uniforms
uniform mat4 u_mvpMatrix;
uniform vec3 u_normal;
uniform mat4 u_normalMatrix;

// Vertex outputs

flat out vec3 v_normal;
out vec2 v_uvCoords;
out vec4 v_clipSpace;

void main(void) {
    v_normal = (u_normalMatrix * vec4(u_normal, 0)).xyz;

    vec4 world = vec4(in_position, 1.0);
    v_clipSpace = u_mvpMatrix * world;
    gl_Position = v_clipSpace;
}
