/**
*
* Ssao Light fragment shader
*
**/

#include "/shaders/common/light/light"
#include "/shaders/common/transform/transform"

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_colourTexture;
uniform sampler2D u_normalTexture;
uniform sampler2D u_specularTexture;
uniform sampler2D u_depthTexture;
uniform sampler2D u_ssaoTexture;

uniform mat4 u_projectionMatrixInv;

uniform DirectionalLight u_light;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3  g_colour;
vec3  g_normal;
float g_specular;
float g_depth;
float g_ssao;
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
    g_ssao = texture(u_ssaoTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    vec3 viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
    g_viewDirection = normalize(-viewPosition);
}

vec3 finalAmbientColour(void) {
    return ambientColour(u_light) * g_ssao;
}

vec3 finalDiffuseColour(void) {
    return diffuseColour(g_normal, u_light);
}

vec3 finalSpecularColour(void) {
    return specularColour(16, g_specular, g_viewDirection, g_normal, u_light);
}