/**
*
* Light Header file
*
**/

#ifndef MAX_DIRECTIONAL_LIGHTS
    #define MAX_DIRECTIONAL_LIGHTS 10
#endif

// Ambient
vec3 totalAmbientColour(const int count, const DirectionalLight lights[MAX_DIRECTIONAL_LIGHTS]);
vec3 ambientColour(const DirectionalLight light);

// Diffuse
vec3 totalDiffuseColour(const vec3 normal, const int count, const DirectionalLight lights[MAX_DIRECTIONAL_LIGHTS]);
vec3 diffuseColour(const vec3 normal, const DirectionalLight lights);

// Specular
vec3 totalSpecularColour(const float power, const float scalar, const vec3 viewDirection,
                         const vec3 normal, const int count, const DirectionalLight lights[MAX_DIRECTIONAL_LIGHTS]);
vec3 specularColour(const float power, const float scalar, const vec3 viewDirection,
                    const vec3 normal, const DirectionalLight light);