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
float g_shadowMapPixelDepth;

// Methods declaration
void initBufferValues(void);
void initGlobals(void);
void initShadowDepth(void);

vec3 ambientColour(void);
vec3 diffuseColour(void);
vec3 specularColour(void);

// Main
void main(void) {
    initBufferValues();
    initGlobals();
    initShadowDepth();

    vec3 ambientColour = ambientColour();

    if (g_shadowDepth < g_shadowMapPixelDepth + SHADOW_BIAS) {
        vec3 diffuseColour = diffuseColour();
        vec3 specularColour = specularColour();
        vec3 finalColour = g_colour * (ambientColour + diffuseColour + specularColour);

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

void initShadowDepth(void) {
    vec4 shadowCoords = shadowMatrix * vec4(g_worldSpace, 1);
    shadowCoords = shadowCoords / shadowCoords.w;
    shadowCoords = shadowCoords * 0.5f + 0.5f;

    g_shadowMapPixelDepth = texture(shadowMap, shadowCoords.xy).x;
    g_shadowDepth = shadowCoords.z;
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