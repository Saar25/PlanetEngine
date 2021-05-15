/**
*
* Flat reflected deferred fragment shader
*
**/

// Vertex outputs

flat in vec3 v_normal;
in vec4 v_clipSpace;
in vec2 cors;

// Uniforms
uniform sampler2D u_reflectionMap;
uniform float     u_specular;

// Fragment outputs
layout (location = 0) out vec4 f_colour;
layout (location = 1) out vec4 f_normal;
layout (location = 2) out vec4 f_specular;

// Methods declaration
vec2 findReflectionUvCoords(void);

void main(void) {
    f_normal = vec4(v_normal, 1);

    vec2 uvCoords = findReflectionUvCoords();
    vec3 colour = texture(u_reflectionMap, uvCoords).rgb;
    f_colour = vec4(colour, 1);

    f_specular = vec4(u_specular, 1, 1, 1);
}

vec2 findReflectionUvCoords(void) {
    vec2 ndc = (v_clipSpace.xy / v_clipSpace.w) * 0.5 + 0.5;
    return vec2(ndc.x, 1 - ndc.y);
}