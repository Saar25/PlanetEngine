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
uniform sampler2D u_colorTexture;
uniform sampler2D u_normalSpecularTexture;
uniform sampler2D u_depthTexture;

uniform mat4 u_projectionMatrixInv;
uniform mat4 u_viewMatrixInv;

uniform int u_directionalLightsCount;
uniform DirectionalLight[MAX_DIRECTIONAL_LIGHTS] u_directionalLights;

uniform int u_pointLightsCount;
uniform PointLight[MAX_POINT_LIGHTS] u_pointLights;

// Fragment outputs
out vec4 f_color;

// Global variables
vec3  g_color;
vec3  g_normal;
float g_specular;
float g_depth;
vec3 g_viewPosition;
vec3  g_viewDirection;

// Methods declaration
void initBufferValues(void);
void initGlobals(void);

vec3 finalLightsColor(void);

// Main
void main(void) {
    initBufferValues();
    initGlobals();
    
    vec3 lightsColor = finalLightsColor();
    vec3 finalColor = g_color * lightsColor;
    
    f_color = vec4(finalColor, 1);
}

void initBufferValues(void) {
    g_color = texture(u_colorTexture, v_position).rgb;
    
    vec4 normalSpecular = texture(u_normalSpecularTexture, v_position);
    g_normal = normalSpecular.rgb;
    g_specular = normalSpecular.a;
    
    g_depth = texture(u_depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    g_viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
    g_viewDirection = normalize(-g_viewPosition);
}

vec3 finalLightsColor(void) {
    return totalLightsColor(u_directionalLights, u_directionalLightsCount, g_normal, g_viewDirection, 16, g_specular) +
            totalLightsColor(u_pointLights, u_pointLightsCount, g_normal, g_viewDirection, g_viewPosition, 16, g_specular);
}