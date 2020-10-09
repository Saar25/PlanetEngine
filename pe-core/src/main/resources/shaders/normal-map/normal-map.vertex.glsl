#version 400

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_uvCoord;
layout (location = 2) in vec3 in_normal;
layout (location = 3) in vec3 in_tangent;
layout (location = 4) in vec3 in_biTangent;

// Uniforms
uniform mat4 u_viewProjection;
uniform mat4 u_transformation;

// Vertex outputs
out vec2 v_uvCoord;
out mat3 v_TBN;

vec4 calculatePosition(in vec3 position) {
    return u_transformation * vec4(position, 1.0);
}

vec3 transformNormal(in vec3 normal) {
    return normalize((u_transformation * vec4(normal, 0.0)).xyz);
}

mat3 calcTbnMatrix(void) {
    const vec3 T = transformNormal(in_tangent);
    const vec3 B = transformNormal(in_biTangent);
    const vec3 N = transformNormal(in_normal);
    return mat3(T, B, N);
}

void main(void) {
    v_uvCoord = in_uvCoord;
    v_TBN = calcTbnMatrix();

    vec4 world = calculatePosition(in_position);
    gl_Position = u_viewProjection * world;
}
