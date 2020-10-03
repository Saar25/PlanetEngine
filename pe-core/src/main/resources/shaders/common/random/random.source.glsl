/**
*
* Random source file
*
**/

// Φ = Golden Ratio
const float PHI = 1.61803398874989484820459;

float goldenNoise(vec2 xy, float seed) {
    float distance = distance(xy * PHI, xy);
    return fract(tan(distance * seed) * xy.x);
}

float random1f(vec4 seed) {
    vec4 root = vec4(12.9898, 78.233, 45.164, 94.673);
    return fract(sin(dot(seed, root)) * 43758.5453);
}