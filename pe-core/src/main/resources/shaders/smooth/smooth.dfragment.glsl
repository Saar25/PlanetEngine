#ifndef FLAT_SHADING
    #define FLAT_SHADING 1
#endif

// Vertex outputs
#if FLAT_SHADING
    flat in vec3 v_colour;
    flat in vec3 v_normal;
#else
    in vec3 v_colour;
    in vec3 v_normal;
#endif

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normal;

void main(void) {
    f_colour = vec4(v_colour, 1);
    f_normal = vec4(v_normal, 1);
}
