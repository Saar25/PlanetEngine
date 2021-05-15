/**
*
* Shadows fragment shader
*
**/

#include "/shaders/common/light/light"
#include "/shaders/common/transform/transform"

// Constants
float SHADOW_BIAS = -0.0001f;

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_shadowMap;
uniform sampler2D u_colourTexture;
uniform sampler2D u_normalTexture;
uniform sampler2D u_specularTexture;
uniform sampler2D u_depthTexture;

uniform mat4 u_shadowMatrix;
uniform mat4 u_projectionMatrixInv;
uniform mat4 u_viewMatrixInv;
uniform vec3 u_cameraWorldPosition;

uniform int u_pcfRadius;

uniform DirectionalLight u_light;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3 g_colour;
vec3 g_normal;
float g_specular;
float g_depth;

vec3 g_viewSpace;
vec3 g_worldSpace;
vec3 g_viewDirection;

float g_shadowDepth;
float g_shadowMapDepth;
vec2 g_shadowMapCoords;

// Methods declaration
void initBufferValues(void);
void initGlobals(void);
void initShadowGlobals(void);

float calcShadowFactor(void);

vec3 ambientColour(void);
vec3 diffuseColour(void);
vec3 specularColour(void);

// Main
void main(void) {
    initBufferValues();
    if (g_depth == 1) {
        discard;
    }

    initGlobals();
    initShadowGlobals();

    vec3 ambientColour = ambientColour();

    float shadowFactor = calcShadowFactor();

    if (shadowFactor > 0) {
        vec3 diffuseColour = diffuseColour();
        vec3 specularColour = specularColour();
        vec3 finalColour = g_colour * (ambientColour + diffuseColour + specularColour) * shadowFactor;

        f_colour = vec4(finalColour, 1);
    } else {
        vec3 finalColour = g_colour * ambientColour;

        f_colour = vec4(finalColour, 1);
    }
}

void initBufferValues(void) {
    g_colour = texture(u_colourTexture, v_position).rgb;
    g_normal = texture(u_normalTexture, v_position).rgb;
    g_specular = texture(u_specularTexture, v_position).r;
    g_depth = texture(u_depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    g_viewSpace = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
    g_worldSpace = viewSpaceToWorldSpace(g_viewSpace, u_viewMatrixInv);
    g_viewDirection = calcViewDirection(u_cameraWorldPosition, g_worldSpace);
}

void initShadowGlobals(void) {
    vec4 shadowCoords = u_shadowMatrix * vec4(g_worldSpace, 1);
    shadowCoords = shadowCoords / shadowCoords.w;
    shadowCoords = shadowCoords * 0.5f + 0.5f;

    g_shadowMapCoords = shadowCoords.xy;
    g_shadowMapDepth = texture(u_shadowMap, shadowCoords.xy).r;
    g_shadowDepth = shadowCoords.z;
}

float calcShadowFactor(void) {
    float shadowFactor = 0.0;
    vec2 inc = 1.0 / textureSize(u_shadowMap, 0);
    for (int row = -u_pcfRadius; row <= u_pcfRadius; row++){
        for (int col = -u_pcfRadius; col <= u_pcfRadius; col++){
            vec2 offset = vec2(row, col) * inc;
            vec2 coords = g_shadowMapCoords + offset;
            float depth = texture(u_shadowMap, coords).r;
            if (g_shadowDepth + SHADOW_BIAS > depth) {
                shadowFactor += 1.0;
            }
        }
    }
    return 1 - shadowFactor / (u_pcfRadius * u_pcfRadius);
}

vec3 ambientColour(void) {
    return ambientColour(u_light);
}

vec3 diffuseColour(void) {
    return diffuseColour(g_normal, u_light);
}

vec3 specularColour(void) {
    return specularColour(16, g_specular, g_viewDirection, g_normal, u_light);
}