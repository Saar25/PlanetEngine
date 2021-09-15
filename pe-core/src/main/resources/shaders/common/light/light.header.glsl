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


float diffuseFactor(const vec3 normal, const vec3 direction);

float specularFactor(const vec3 normal, const vec3 lightDirection, vec3 viewDirection);

float smoothSpecular(const float specular, const float power, const float scalar);

// Directional light

// Ambient
vec3 totalAmbientColour(const int count, const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights);
vec3 ambientColour(const DirectionalLight light);

// Diffuse
vec3 totalDiffuseColour(const vec3 normal, const int count, const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights);
vec3 diffuseColour(const vec3 normal, const DirectionalLight lights);

// Specular
vec3 totalSpecularColour(const float power, const float scalar, const vec3 viewDirection,
                         const vec3 normal, const int count, const DirectionalLight[MAX_DIRECTIONAL_LIGHTS] lights);
vec3 specularColour(const float power, const float scalar, const vec3 viewDirection,
                    const vec3 normal, const DirectionalLight light);

// Point light

// Ambient
vec3 totalAmbientColour(const int count, const PointLight[MAX_POINT_LIGHTS] lights);
vec3 ambientColour(const PointLight light);

// Diffuse
vec3 totalDiffuseColour(const vec3 normal, const vec3 viewPosition, const int count, const PointLight[MAX_POINT_LIGHTS] lights);
vec3 diffuseColour(const vec3 normal, const vec3 viewPosition, const PointLight lights);

// Specular
vec3 totalSpecularColour(const float power, const float scalar, const vec3 viewPosition, const vec3 viewDirection,
                         const vec3 normal, const int count, const PointLight[MAX_POINT_LIGHTS] lights);
vec3 specularColour(const float power, const float scalar, const vec3 viewPosition,
                    const vec3 viewDirection, const vec3 normal, const PointLight light);

