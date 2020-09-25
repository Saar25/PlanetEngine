#version 400

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
vec3 g_viewPosition;
vec3 g_worldPosition;
float g_shadowDepth;
float g_shadowMapPixelDepth;

vec4 shadowCoords;

// Methods declaration
void initBufferValues(void);
void initViewPosition(void);
void initWorldPosition(void);
void initShadowDepth(void);

// Main
void main(void) {
    initBufferValues();
    initViewPosition();
    initWorldPosition();
    initShadowDepth();

    if (g_shadowDepth < g_shadowMapPixelDepth + SHADOW_BIAS) {
        f_colour = vec4(g_colour, 1);
    } else {
        f_colour = vec4(g_colour * .2, 1);
    }
//    f_colour = texture(shadowMap, v_position).rrrr;
//    f_colour = vec4(g_colour, 1);
//    f_colour = vec4(g_shadowDepth, 1);
}

void initBufferValues(void) {
    g_colour = texture(colourTexture, v_position).rgb;
    g_normal = texture(normalTexture, v_position).xyz;
    g_depth = texture(depthTexture, v_position).r;
}

void initViewPosition(void) {
    float x = v_position.x, y = v_position.y, z = g_depth;
    vec4 clipSpacePosition = vec4(vec3(x, y, z) * 2.0 - 1.0, 1.0);
    vec4 viewSpacePosition = projectionMatrixInv * clipSpacePosition;
    g_viewPosition = viewSpacePosition.xyz / viewSpacePosition.w;
}

void initWorldPosition(void) {
    vec4 worldSpacePosition = viewMatrixInv * vec4(g_viewPosition, 1);
    g_worldPosition = worldSpacePosition.xyz / worldSpacePosition.w;
}

void initShadowDepth(void) {
    shadowCoords = shadowMatrix * vec4(g_worldPosition, 1);
    shadowCoords = shadowCoords / shadowCoords.w;
    shadowCoords = shadowCoords * 0.5f + 0.5f;

    g_shadowMapPixelDepth = texture(shadowMap, shadowCoords.xy).x;
    g_shadowDepth = shadowCoords.z;
}