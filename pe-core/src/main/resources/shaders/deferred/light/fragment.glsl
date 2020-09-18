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

uniform DirectionalLight[MAX_DIRECTIONAL_LIGHTS] directionalLights;
uniform PointLight[MAX_POINT_LIGHTS] pointLights;

// Fragment outputs
out vec4 f_colour;

vec3 colour;
vec3 normal;
float depth;

vec3 diffuseColour(DirectionalLight light);

void main(void) {
    colour = texture(colourTexture, v_position).rgb;
    normal = texture(normalTexture, v_position).xyz;
    depth = texture(depthTexture, v_position).r;

    DirectionalLight light = directionalLights[0];
    vec3 diffuseColour = diffuseColour(light);

    f_colour = vec4(colour * diffuseColour, 1);
}

vec3 diffuseColour(DirectionalLight light) {
    float diffuse = dot(normal, -light.direction);
    return light.colour * max(diffuse, 0);
}