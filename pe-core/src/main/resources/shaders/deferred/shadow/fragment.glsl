/**
*
* Shadows fragment shader
*
**/

// Constants
float SHADOW_BIAS = -0.0001f;

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D shadowMap;
uniform sampler2D colourTexture;
uniform sampler2D normalTexture;
uniform sampler2D depthTexture;

uniform mat4 shadowMatrix;
uniform mat4 projectionMatrixInv;
uniform mat4 viewMatrixInv;
uniform vec3 cameraWorldPosition;

uniform int pcfRadius;

uniform DirectionalLight light;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3 g_colour;
vec3 g_normal;
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
    g_colour = texture(colourTexture, v_position).rgb;
    g_normal = texture(normalTexture, v_position).xyz;
    g_depth = texture(depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    g_viewSpace = clipSpaceToViewSpace(clipSpace, projectionMatrixInv);
    g_worldSpace = viewSpaceToWorldSpace(g_viewSpace, viewMatrixInv);
    g_viewDirection = calcViewDirection(cameraWorldPosition, g_worldSpace);
}

void initShadowGlobals(void) {
    vec4 shadowCoords = shadowMatrix * vec4(g_worldSpace, 1);
    shadowCoords = shadowCoords / shadowCoords.w;
    shadowCoords = shadowCoords * 0.5f + 0.5f;

    g_shadowMapCoords = shadowCoords.xy;
    g_shadowMapDepth = texture(shadowMap, shadowCoords.xy).r;
    g_shadowDepth = shadowCoords.z;
}

float calcShadowFactor(void) {
    float shadowFactor = 0.0;
    vec2 inc = 1.0 / textureSize(shadowMap, 0);
    for (int row = -pcfRadius; row <= pcfRadius; row++){
        for (int col = -pcfRadius; col <= pcfRadius; col++){
            vec2 offset = vec2(row, col) * inc;
            vec2 coords = g_shadowMapCoords + offset;
            float depth = texture(shadowMap, coords).r;
            if (g_shadowDepth + SHADOW_BIAS > depth) {
                shadowFactor += 1.0;
            }
        }
    }
    return 1 - shadowFactor / (pcfRadius * pcfRadius);
}

vec3 ambientColour(void) {
    return ambientColour(light);
}

vec3 diffuseColour(void) {
    return diffuseColour(g_normal, light);
}

vec3 specularColour(void) {
    return specularColour(16, 2.5, g_viewDirection, g_normal, light);
}