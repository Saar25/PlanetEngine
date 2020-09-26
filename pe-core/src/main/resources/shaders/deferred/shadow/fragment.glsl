/**
*
* Shadows fragment shader
*
**/

// Constants
float SHADOW_BIAS = 0.001f;

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

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3 g_colour;
vec3 g_normal;
float g_depth;
vec3 g_viewSpace;
vec3 g_worldSpace;
float g_shadowDepth;
float g_shadowMapPixelDepth;

// Methods declaration
void initBufferValues(void);
void initGlobals(void);
void initShadowDepth(void);

// Main
void main(void) {
    initBufferValues();
    initGlobals();
    initShadowDepth();

    if (g_shadowDepth < g_shadowMapPixelDepth + SHADOW_BIAS) {
        f_colour = vec4(g_colour, 1);
    } else {
        f_colour = vec4(g_colour * .2, 1);
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
}

void initShadowDepth(void) {
    vec4 shadowCoords = shadowMatrix * vec4(g_worldSpace, 1);
    shadowCoords = shadowCoords / shadowCoords.w;
    shadowCoords = shadowCoords * 0.5f + 0.5f;

    g_shadowMapPixelDepth = texture(shadowMap, shadowCoords.xy).x;
    g_shadowDepth = shadowCoords.z;
}