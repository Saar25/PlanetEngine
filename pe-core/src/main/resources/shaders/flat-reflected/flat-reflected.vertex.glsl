/**
*
* Flat reflected vertex shader
*
**/

const vec2 uvCoords[] = {
    vec2(0, 0), vec2(1, 0),
    vec2(0, 1), vec2(1, 1)
};

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec3 in_normal;

// Uniforms
uniform mat4 mvpMatrix;

// Vertex outputs

#if FLAT_SHADING
    flat out vec3 v_normal;
#else
    out vec3 v_normal;
#endif
out vec2 v_uvCoords;

void main(void) {
    v_normal = in_normal;

    v_uvCoords = uvCoords[gl_VertexID];

    vec4 world = in_transformation * vec4(in_position, 1.0);
    gl_Position = mvpMatrix * world;
}
