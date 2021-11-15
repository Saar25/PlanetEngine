/**
*
* Random source file
*
**/

#define RANDOM(seed) (fract(sin(seed) * 43758.5453))

float random(float seed) {
    float root = 12.9898;
    return RANDOM(seed * root);
}

float random(vec2 seed) {
    vec2 root = vec2(12.9898, 78.233);
    return RANDOM(dot(seed, root));
}

float random(vec3 seed) {
    vec3 root = vec3(12.9898, 78.233, 45.164);
    return RANDOM(dot(seed, root));
}

float random(vec4 seed) {
    vec4 root = vec4(12.9898, 78.233, 45.164, 94.673);
    return RANDOM(dot(seed, root));
}