/**
*
* Light fragment shader
*
**/

#include "/shaders/common/light/light"
#include "/shaders/common/transform/transform"

// definitions
#ifndef MAX_DIRECTIONAL_LIGHTS
    #define MAX_DIRECTIONAL_LIGHTS 10
#endif
#ifndef MAX_POINT_LIGHTS
    #define MAX_POINT_LIGHTS 10
#endif

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_colourTexture;
uniform sampler2D u_normalTexture;
uniform sampler2D u_specularTexture;
uniform sampler2D u_depthTexture;

uniform mat4 u_projectionMatrixInv;
uniform mat4 u_viewMatrixInv;

uniform int u_directionalLightsCount;
uniform DirectionalLight[MAX_DIRECTIONAL_LIGHTS] u_directionalLights;

uniform int u_pointLightsCount;
uniform PointLight[MAX_POINT_LIGHTS] u_pointLights;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3  g_colour;
vec3  g_normal;
float g_specular;
float g_depth;
vec3 g_viewPosition;
vec3  g_viewDirection;

// Methods declaration
void initBufferValues(void);
void initGlobals(void);

vec3 finalAmbientColour(void);
vec3 finalDiffuseColour(void);
vec3 finalSpecularColour(void);

// Main
void main(void) {
    initBufferValues();
    initGlobals();

    vec3 ambientColour = finalAmbientColour();
    vec3 diffuseColour = finalDiffuseColour();
    vec3 specularColour = finalSpecularColour();
    vec3 finalColour = g_colour * (ambientColour + diffuseColour + specularColour);

    f_colour = vec4(finalColour, 1);
}

void initBufferValues(void) {
    g_colour = texture(u_colourTexture, v_position).rgb;
    g_normal = texture(u_normalTexture, v_position).rgb;
    g_specular = texture(u_specularTexture, v_position).r;
    g_depth = texture(u_depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    g_viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
    g_viewDirection = normalize(-g_viewPosition);
}

vec3 finalAmbientColour(void) {
    return totalAmbientColour(u_directionalLightsCount, u_directionalLights);
}

vec3 finalDiffuseColour(void) {
    return totalDiffuseColour(g_normal, u_directionalLightsCount, u_directionalLights) +
        totalDiffuseColour(g_normal, g_viewPosition, u_pointLightsCount, u_pointLights);
}

vec3 finalSpecularColour(void) {
    return totalSpecularColour(16, g_specular, g_viewDirection,
            g_normal, u_directionalLightsCount, u_directionalLights) +
        totalSpecularColour(16, g_specular, g_viewPosition, g_viewDirection,
            g_normal, u_pointLightsCount, u_pointLights);
}