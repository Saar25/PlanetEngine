/**
*
* Light fragment shader
*
**/

// definitions
#define MAX_DIRECTIONAL_LIGHTS 2
#define MAX_POINT_LIGHTS 5

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D colourTexture;
uniform sampler2D normalTexture;
uniform sampler2D depthTexture;

uniform vec3 cameraWorldPosition;
uniform mat4 projectionMatrixInv;
uniform mat4 viewMatrixInv;

uniform int directionalLightsCount;
uniform DirectionalLight[MAX_DIRECTIONAL_LIGHTS] directionalLights;

uniform int pointLightsCount;
uniform PointLight[MAX_POINT_LIGHTS] pointLights;

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

vec3 findNormal(vec2 normal);

vec3 finalAmbientColour(void);
vec3 finalDiffuseColour(void);
vec3 finalSpecularColour(void);

// Main
void main(void) {
    initBufferValues();
    if (g_depth == 1) {
        discard;
    }

    initGlobals();

    vec3 ambientColour = finalAmbientColour();
    vec3 diffuseColour = finalDiffuseColour();
    vec3 specularColour = finalSpecularColour();
    vec3 finalColour = g_colour * (ambientColour + diffuseColour + specularColour);

    f_colour = vec4(finalColour, 1);
}

void initBufferValues(void) {
    g_colour = texture(colourTexture, v_position).rgb;
    g_normal = texture(normalTexture, v_position).xyz;
    g_depth = texture(depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    g_viewPosition = clipSpaceToViewSpace(clipSpace, projectionMatrixInv);
    g_worldPosition = viewSpaceToWorldSpace(g_viewPosition, viewMatrixInv);
    g_viewDirection = calcViewDirection(cameraWorldPosition, g_worldPosition);
}

vec3 findNormal(vec2 normal) {
    float x2 = pow(normal.x, 2);
    float y2 = pow(normal.y, 2);
    float z = sqrt(1 - x2 - y2);
    return vec3(normal, z);
}

vec3 finalAmbientColour(void) {
    return totalAmbientColour(directionalLightsCount, directionalLights);
}

vec3 finalDiffuseColour(void) {
    return totalDiffuseColour(g_normal, directionalLightsCount, directionalLights);
}

vec3 finalSpecularColour(void) {
    return totalSpecularColour(16, 2.5, g_viewDirection, g_normal, directionalLightsCount, directionalLights);
}