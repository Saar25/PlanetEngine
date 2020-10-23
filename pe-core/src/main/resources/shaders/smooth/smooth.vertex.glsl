#version 400

#ifndef FLAT_SHADING
    #define FLAT_SHADING 1
#endif

// Per Vertex attibutes
layout (location = 0) in vec3 in_position;
layout (location = 1) in vec3 in_normal;
layout (location = 2) in vec3 in_colour;
layout (location = 3) in vec3 in_target;

// Uniforms
uniform mat4 u_mvpMatrix;
uniform float u_targetScalar;

// Vertex outputs
#if FLAT_SHADING
    flat out vec3 v_colour;
    flat out vec3 v_normal;
#else
    out vec3 v_colour;
    out vec3 v_normal;
#endif

void main(void) {
    float targetScalar = smoothstep(0, 1, u_targetScalar);
    vec3 target = targetScalar * in_target;
    vec3 position = in_position + target;
    vec3 normal = in_normal + target;

    v_colour = in_colour;
    v_normal = normalize(normal);

    vec4 world = vec4(position, 1.0);
    gl_Position = u_mvpMatrix * world;
}
