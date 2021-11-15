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
uniform sampler2D u_normalSpecularTexture;
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

vec3 finalLightColour(void);

// Main
void main(void) {
    initBufferValues();
    initGlobals();
    
    vec3 lightsColour = finalLightsColour() * g_ssao;
    vec3 finalColour = g_colour * lightsColour;
    
    f_colour = vec4(finalColour, 1);
}

void initBufferValues(void) {
    g_colour = texture(u_colourTexture, v_position).rgb;
    
    vec4 normalSpecular = texture(u_normalSpecularTexture, v_position);
    g_normal = normalSpecular.rgb;
    g_specular = normalSpecular.a;
    
    g_depth = texture(u_depthTexture, v_position).r;
    g_ssao = texture(u_ssaoTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    vec3 viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
    g_viewDirection = normalize(-viewPosition);
}

vec3 finalLightColour(void) {
    vec3 reflectedViewDirection = reflect(g_viewDirection, g_normal);
    return lightColour(u_light, g_normal, reflectedViewDirection, 16, g_specular);
}