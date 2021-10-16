/**
*
* Light Header file
*
**/

#ifndef MAX_DIRECTIONAL_LIGHTS
    #define MAX_DIRECTIONAL_LIGHTS 10
#endif

#ifndef MAX_POINT_LIGHTS
    #define MAX_POINT_LIGHTS 10
#endif


float ambientFactor();

float diffuseFactor(const vec3 normal, const vec3 direction);

float specularFactor(const vec3 normal, const vec3 lightDirection, vec3 viewDirection);

float smoothSpecularFactor(const float specular, const float power, const float scalar);

// Directional light

float lightFactor(const DirectionalLight light, const vec3 normal, const vec3 reflectedViewDirection, const float power, const float scalar);

vec3 lightColour(const DirectionalLight light, const vec3 normal, const vec3 reflectedViewDirection, const float power, const float scalar);

vec3 totalLightsColour(const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights, const int count,
                       const vec3 normal, const vec3 viewDirection, const float power, const float scalar);

// Point light

float lightFactor(const PointLight light, const vec3 normal, const vec3 reflectedViewDirection, const vec3 viewPosition, const float power, const float scalar);

vec3 lightColour(const PointLight light, const vec3 normal, const vec3 reflectedViewDirection, const vec3 viewPosition, const float power, const float scalar);

vec3 totalLightsColour(const PointLight[MAX_POINT_LIGHTS] lights, const int count, const vec3 normal,
                       const vec3 viewDirection, const vec3 viewPosition, const float power, const float scalar);