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

// Utility

float ambientFactor() {
    return .3;
}

float diffuseFactor(const vec3 normal, const vec3 direction) {
    return max(dot(normal, -direction), 0);
}

float specularFactor(const vec3 lightDirection, vec3 reflectedViewDirection) {
    return max(dot(lightDirection, reflectedViewDirection), 0);
}

float smoothSpecularFactor(const float specular, const float power, const float scalar) {
    return pow(max(specular, 0), power) * scalar;
}

// Directional light

float lightFactor(const DirectionalLight light, const vec3 normal, const vec3 reflectedViewDirection,
                  const float power, const float scalar, const float shadow) {
    float diffuse = diffuseFactor(normal, light.direction);
    if (diffuse > 0) {
        float specularFactor = specularFactor(light.direction, reflectedViewDirection);
        float smoothSpecularFactor = smoothSpecularFactor(specularFactor, power, scalar);
        return ambientFactor() + (diffuse + smoothSpecularFactor) * shadow;
    } else {
        return ambientFactor() + (diffuse) * shadow;
    }
}

vec3 lightColor(const DirectionalLight light, const vec3 normal, const vec3 reflectedViewDirection,
                 const float power, const float scalar, const float shadow) {
    return light.color * lightFactor(light, normal, reflectedViewDirection, power, scalar, shadow);
}

vec3 lightColor(const DirectionalLight light, const vec3 normal, const vec3 reflectedViewDirection, const float power, const float scalar) {
    return lightColor(light, normal, reflectedViewDirection, power, scalar, 1);
}

vec3 totalLightsColor(const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights, const int count,
                       const vec3 normal, const vec3 viewDirection, const float power, const float scalar) {
    vec3 reflectedViewDirection = reflect(viewDirection, normal);
    vec3 color = vec3(0, 0, 0);
    for (int i = 0; i < count; i++) {
        color += lightColor(lights[i], normal, reflectedViewDirection, power, scalar);
    }
    return color;
}

// Point light

float lightFactor(const PointLight light, const vec3 normal, const vec3 reflectedViewDirection,
                  const vec3 viewPosition, const float power, const float scalar) {
    vec3 a = light.attenuation;
    float d = distance(viewPosition, light.position);
    float attenuation = a.x + a.y * d + a.z * d * d;
    vec3 direction = normalize(viewPosition - light.position);
    
    float diffuse = diffuseFactor(normal, direction);
    if (diffuse > 0) {
        float specularFactor = specularFactor(direction, reflectedViewDirection);
        float smoothSpecularFactor = smoothSpecularFactor(specularFactor, power, scalar);
        return (ambientFactor() + diffuse + smoothSpecularFactor) / attenuation;
    } else {
        return (ambientFactor() + diffuse) / attenuation;
    }
}

vec3 lightColor(const PointLight light, const vec3 normal, const vec3 reflectedViewDirection,
                 const vec3 viewPosition, const float power, const float scalar) {
    return light.color * lightFactor(light, normal, reflectedViewDirection, viewPosition, power, scalar);
}

vec3 totalLightsColor(const PointLight[MAX_POINT_LIGHTS] lights, const int count, const vec3 normal,
                       const vec3 viewDirection, const vec3 viewPosition, const float power, const float scalar) {
    vec3 reflectedViewDirection = reflect(viewDirection, normal);
    vec3 color = vec3(0, 0, 0);
    for (int i = 0; i < count; i++) {
        color += lightColor(lights[i], normal, viewDirection, viewPosition, power, scalar);
    }
    return color;
}