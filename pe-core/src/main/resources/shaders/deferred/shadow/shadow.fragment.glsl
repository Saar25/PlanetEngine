/**
*
* Shadows fragment shader
*
**/

#include "/shaders/common/light/light"
#include "/shaders/common/random/random"
#include "/shaders/common/transform/transform"

// Definitions
#ifndef SHADOW_BIAS
#define SHADOW_BIAS 0.001f
#endif

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_shadowMap;
uniform sampler2D u_colourTexture;
uniform sampler2D u_normalSpecularTexture;
uniform sampler2D u_depthTexture;

uniform mat4 u_shadowMatrix;
uniform mat4 u_projectionMatrixInv;
uniform mat4 u_viewMatrixInv;

uniform ivec2 u_shadowMapSize;
uniform int   u_pcfRadius;

uniform DirectionalLight u_light;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3 g_colour;
vec3 g_normal;
float g_specular;
float g_depth;

vec3 g_worldSpace;
vec3 g_viewDirection;

float g_shadowDepth;
vec2 g_shadowMapCoords;

// Methods declaration
void initBufferValues(void);
void initGlobals(void);
void initShadowGlobals(void);

float calcShadowFactor(void);

// Main
void main(void) {
    initBufferValues();
    initGlobals();
    initShadowGlobals();

    vec3 ambientColour = u_light.colour * ambientFactor();

    float shadowFactor = calcShadowFactor();

    if (shadowFactor > 0) {
        vec3 reflectedViewDirection = reflect(g_viewDirection, g_normal);
        vec3 lightColour = lightColour(u_light, g_normal, reflectedViewDirection, 16, g_specular);
        vec3 finalColour = g_colour * lightColour * shadowFactor;

        f_colour = vec4(finalColour, 1);
    } else {
        vec3 finalColour = g_colour * ambientColour;

        f_colour = vec4(finalColour, 1);
    }
}

void initBufferValues(void) {
    g_colour = texture(u_colourTexture, v_position).rgb;
    
    vec4 normalSpecular = texture(u_normalSpecularTexture, v_position);
    g_normal = normalSpecular.rgb;
    g_specular = normalSpecular.a;
    
    g_depth = texture(u_depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    vec3 viewSpace = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
    g_worldSpace = viewSpaceToWorldSpace(viewSpace, u_viewMatrixInv);
    g_viewDirection = normalize(-viewSpace);
}

void initShadowGlobals(void) {
    vec4 shadowCoords = u_shadowMatrix * vec4(g_worldSpace, 1);
    shadowCoords = shadowCoords / shadowCoords.w;
    shadowCoords = shadowCoords * 0.5f + 0.5f;

    g_shadowMapCoords = shadowCoords.xy;
    g_shadowDepth = shadowCoords.z;
}

float calcShadowFactor(void) {
    float shadowFactor = 0.0;
    vec2 inc = 1.0 / textureSize(u_shadowMap, 0);
    for (int row = -u_pcfRadius; row <= u_pcfRadius; row++){
        for (int col = -u_pcfRadius; col <= u_pcfRadius; col++){
            vec2 random = vec2(random(fract(g_worldSpace.xy)), random(fract(g_worldSpace.yx)));
            vec2 offset = vec2(row, col) * random * inc;
            vec2 coords = g_shadowMapCoords + offset;
            float depth = texture(u_shadowMap, coords).r;
            if (g_shadowDepth + SHADOW_BIAS > depth) {
                shadowFactor += 1.0;
            }
        }
    }
    return 1 - shadowFactor / (u_pcfRadius * u_pcfRadius + 1);
}