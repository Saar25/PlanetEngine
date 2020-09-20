#version 400
#define MAX_DIRECTIONAL_LIGHTS 2
#define MAX_POINT_LIGHTS 5

// Structs
struct DirectionalLight {
    vec3 direction;
    vec3 colour;
};

struct PointLight {
    vec3 position;
    vec3 radiuses;
    vec3 colour;
};

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

// Methods declaration

void initBufferValues(void);
void initViewPosition(void);
void initWorldPosition(void);
vec3 findNormal(vec2 normal);

vec3 finalAmbientColour(void);
vec3 ambientColour(const DirectionalLight light);

vec3 finalDiffuseColour(void);
vec3 diffuseColour(const DirectionalLight light);

vec3 finalSpecularColour(void);
vec3 specularColour(const DirectionalLight light);


// Main
void main(void) {
    initBufferValues();
    if (g_depth == 1) {
        discard;
    }

    initViewPosition();
    initWorldPosition();

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

    // g_normal = findNormal(texture(normalTexture, v_position).xy);
}

void initViewPosition(void) {
    float x = v_position.x, y = v_position.y, z = g_depth;
    vec4 clipSpacePosition = vec4(vec3(x, y, z) * 2.0 - 1.0, 1.0);
    vec4 viewSpacePosition = projectionMatrixInv * clipSpacePosition;
    g_viewPosition = viewSpacePosition.rgb / viewSpacePosition.w;
}

void initWorldPosition(void) {
    vec4 worldSpacePosition = viewMatrixInv * vec4(g_viewPosition, 1);
    g_worldPosition = worldSpacePosition.xyz / worldSpacePosition.w;
}

vec3 findNormal(vec2 normal) {
    float x2 = pow(normal.x, 2);
    float y2 = pow(normal.y, 2);
    float z = sqrt(1 - x2 - y2);
    return vec3(normal, z);
}

vec3 finalAmbientColour(void) {
    vec3 ambientColour = vec3(0, 0, 0);
    for (int i = 0; i < directionalLightsCount; i++) {
        DirectionalLight light = directionalLights[i];
        ambientColour += ambientColour(light);
    }
    return ambientColour;
}

vec3 ambientColour(const DirectionalLight light) {
    return light.colour * 0.1;
}

vec3 finalDiffuseColour(void) {
    vec3 diffuseColour = vec3(0, 0, 0);
    for (int i = 0; i < directionalLightsCount; i++) {
        DirectionalLight light = directionalLights[i];
        diffuseColour += diffuseColour(light);
    }
    return diffuseColour;
}

vec3 diffuseColour(const DirectionalLight light) {
    float diffuse = dot(g_normal, -light.direction);
    return light.colour * max(diffuse, 0);
}

vec3 finalSpecularColour(void) {
    vec3 specularColour = vec3(0, 0, 0);
    for (int i = 0; i < directionalLightsCount; i++) {
        DirectionalLight light = directionalLights[i];
        specularColour += specularColour(light);
    }
    return specularColour;
}

vec3 specularColour(const DirectionalLight light) {
    const float specularPower = 16, specularScalar = 2.5;

    vec3 reflect = reflect(light.direction, g_normal);
    vec3 viewDirection = -normalize(g_worldPosition - cameraWorldPosition);
    float specular = dot(reflect, viewDirection);
    specular = pow(specular, specularPower) * specularScalar;
    return specular * light.colour;
}