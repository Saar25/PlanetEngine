/**
*
* Light Structs file
*
**/

// Point light
struct PointLight {
    vec3 position;
    vec3 radiuses;
    vec3 colour;
};

// Directional light
struct DirectionalLight {
    vec3 direction;
    vec3 colour;
};