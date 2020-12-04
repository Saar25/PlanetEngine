/**
*
* Flat reflected fragment shader
*
**/

// Vertex outputs

flat in vec3 v_normal;
in vec2 v_uvCoords;

// Uniforms
uniform sampler2D u_reflectionMap;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normal;

void main(void) {
    f_normal = vec4(v_normal, 1.0);

    f_colour = vec4(texture(u_reflectionMap, v_uvCoords).rgb, 1.0);
}
