/**
*
* Light source file
*
**/

#ifndef MAX_DIRECTIONAL_LIGHTS
    #define MAX_DIRECTIONAL_LIGHTS 10
#endif

vec3 ambientColour(const DirectionalLight light) {
    return light.colour * 0.1;
}

vec3 diffuseColour(const vec3 normal, const DirectionalLight light) {
    float diffuse = dot(normal, -light.direction);
    return light.colour * max(diffuse, 0);
}

vec3 specularColour(const float power, const float scalar, const vec3 viewDirection,
                    const vec3 normal, const DirectionalLight light) {
    if (dot(-light.direction, normal) > 0) {
        vec3 reflect = reflect(light.direction, normal);
        float specular = dot(reflect, viewDirection);
        specular = pow(max(specular, 0), power) * scalar;
        return specular * light.colour;
    }
    return vec3(0);
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