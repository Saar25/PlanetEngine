/**
*
* Ssao fragment shader
*
**/

#include "/shaders/common/transform/transform"

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_colourTexture;
uniform sampler2D u_normalTexture;
uniform sampler2D u_depthTexture;

uniform vec3 u_cameraWorldPosition;
uniform mat4 u_projectionMatrixInv;
uniform mat4 u_viewMatrixInv;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3 g_colour;
vec3 g_normal;
float g_depth;
vec3 g_viewPosition;
vec3 g_worldPosition;
vec3 g_viewDirection;

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

    vec3 finalColour = g_colour;

    f_colour = vec4(finalColour, 1);
}

void initBufferValues(void) {
    g_colour = texture(u_colourTexture, v_position).rgb;
    g_normal = texture(u_normalTexture, v_position).rgb;
    g_depth = texture(u_depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    g_viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
    g_worldPosition = viewSpaceToWorldSpace(g_viewPosition, u_viewMatrixInv);
    g_viewDirection = calcViewDirection(u_cameraWorldPosition, g_worldPosition);
}