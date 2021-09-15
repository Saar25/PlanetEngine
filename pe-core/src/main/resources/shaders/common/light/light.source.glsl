/**
*
* Light source file
*
**/

#ifndef MAX_DIRECTIONAL_LIGHTS
    #define MAX_DIRECTIONAL_LIGHTS 10
#endif

#ifndef MAX_POINT_LIGHTS
#define MAX_POINT_LIGHTS 10
#endif

float diffuseFactor(const vec3 normal, const vec3 direction) {
    return max(dot(normal, -direction), 0);
}

float specularFactor(const vec3 normal, const vec3 lightDirection, vec3 viewDirection) {
    if (dot(-lightDirection, normal) > 0) {
        vec3 reflect = reflect(lightDirection, normal);
        return dot(reflect, viewDirection);
    }
    return 0;
}

float smoothSpecular(const float specular, const float power, const float scalar) {
    return pow(max(specular, 0), power) * scalar;
}

vec3 ambientColour(const DirectionalLight light) {
    return light.colour * 0.3;
}

vec3 diffuseColour(const vec3 normal, const DirectionalLight light) {
    return light.colour * diffuseFactor(normal, light.direction);
}

vec3 specularColour(const float power, const float scalar, const vec3 viewDirection,
                    const vec3 normal, const DirectionalLight light) {
    float specular = specularFactor(normal, light.direction, viewDirection);
    return light.colour * smoothSpecular(specular, power, scalar);
}

vec3 totalAmbientColour(const int count, const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights) {
    vec3 colour = vec3(0, 0, 0);
    for (int i = 0; i < count; i++) {
        colour += ambientColour(lights[i]);
    }
    return colour;
}

vec3 totalDiffuseColour(const vec3 normal, const int count, const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights) {
    vec3 color = vec3(0, 0, 0);
    for (int i = 0; i < count; i++) {
        DirectionalLight light = lights[i];
        color += diffuseColour(normal, light);
    }
    return color;
}

vec3 totalSpecularColour(const float power, const float scalar, const vec3 viewDirection,
                         const vec3 normal, const int count, const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights) {
    vec3 color = vec3(0, 0, 0);
    for (int i = 0; i < count; i++) {
        DirectionalLight light = lights[i];
        color += specularColour(power, scalar, viewDirection, normal, light);
    }
    return color;
}

vec3 ambientColour(const PointLight light) {
    return light.colour * 0.0;
}

vec3 diffuseColour(const vec3 normal, const vec3 viewPosition, const PointLight light) {
    float distance = distance(viewPosition, light.position);
    float attenuation = light.attenuation.x + light.attenuation.y * distance + light.attenuation.z * distance * distance;
    vec3 direction = normalize(viewPosition - light.position);
    return light.colour * diffuseFactor(normal, direction) / attenuation;
}

vec3 specularColour(const float power, const float scalar, const vec3 viewPosition,
                    const vec3 viewDirection, const vec3 normal, const PointLight light) {
    float distance = distance(viewPosition, light.position);
    float attenuation = light.attenuation.x + light.attenuation.y * distance + light.attenuation.z * distance * distance;
    vec3 direction = normalize(viewPosition - light.position);
    float specular = specularFactor(normal, direction, viewDirection) / attenuation;
    return light.colour * smoothSpecular(specular, power, scalar);
}

vec3 totalAmbientColour(const int count, const PointLight[MAX_POINT_LIGHTS] lights) {
    return vec3(0, 0, 0);
}

vec3 totalDiffuseColour(const vec3 normal, const vec3 viewPosition, const int count, const PointLight[MAX_POINT_LIGHTS] lights) {
    vec3 color = vec3(0, 0, 0);
    for (int i = 0; i < count; i++) {
        PointLight light = lights[i];
        color += diffuseColour(normal, viewPosition, light);
    }
    return color;
}

vec3 totalSpecularColour(const float power, const float scalar, const vec3 viewPosition, const vec3 viewDirection,
                         const vec3 normal, const int count, const PointLight[MAX_POINT_LIGHTS] lights) {
    vec3 color = vec3(0, 0, 0);
    for (int i = 0; i < count; i++) {
        PointLight light = lights[i];
        color += specularColour(power, scalar, viewPosition, viewDirection, normal, light);
    }
    return color;
}