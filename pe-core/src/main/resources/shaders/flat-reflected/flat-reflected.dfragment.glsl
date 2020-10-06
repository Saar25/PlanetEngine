/**
*
* Flat reflected deferred fragment shader
*
**/

#ifndef FLAT_SHADING
    #define FLAT_SHADING true
#endif

// Vertex outputs

#if FLAT_SHADING
    flat in vec3 v_normal;
#else
    in vec3 v_normal;
#endif
in vec2 v_uvCoords;

// Uniforms
uniform sampler2D reflectionMap;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normal;

void main(void) {
    f_normal = vec4(v_normal, 1);

    f_colour = vec4(texture(reflectionMap, v_uvCoords).rgb, 1.0);
}
