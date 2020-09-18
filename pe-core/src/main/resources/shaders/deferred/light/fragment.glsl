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

uniform mat4 projectionMatrixInv;

uniform int directionalLightsCount;
uniform DirectionalLight[MAX_DIRECTIONAL_LIGHTS] directionalLights;

uniform int pointLightsCount;
uniform PointLight[MAX_POINT_LIGHTS] pointLights;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3 colour;
vec3 normal;
float depth;
vec3 viewPosition;

// Methods declaration

void initBufferValues();
vec3 initViewPosition();
vec3 finalDiffuseColour();
vec3 diffuseColour(DirectionalLight light);

// Main
void main(void) {
    initBufferValues();
    initViewPosition();

    vec3 diffuseColour = finalDiffuseColour();

    f_colour = vec4(colour * diffuseColour, 1);
}

void initBufferValues() {
    colour = texture(colourTexture, v_position).rgb;
    normal = texture(normalTexture, v_position).xyz;
    depth = texture(depthTexture, v_position).r;
}

vec3 initViewPosition() {
    float z = depth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(v_position * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = projectionMatrixInv * clipSpacePosition;
    return viewSpacePosition.rgb / viewSpacePosition.w;
}

vec3 finalDiffuseColour() {
    vec3 diffuseColour = vec3(0, 0, 0);
    for (int i = 0; i < directionalLightsCount; i++) {
        DirectionalLight light = directionalLights[i];
        diffuseColour += diffuseColour(light);
    }
    return diffuseColour;
}

vec3 diffuseColour(DirectionalLight light) {
    float diffuse = dot(normal, -light.direction);
    return light.colour * max(diffuse, 0);
}

vec3 specularColour(DirectionalLight light) {
    float diffuse = dot(normal, -light.direction);
    return light.colour * max(diffuse, 0);
}