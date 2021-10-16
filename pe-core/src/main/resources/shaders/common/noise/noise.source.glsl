/**
*
* Noise source file
*
**/

#include "/shaders/common/random/random"

float noise(float seed) {
    float i = floor(seed);
    float f = fract(seed);
    
    float a = random(i);
    float b = random(i + 1.0);
    
    float u = smoothstep(0, 1, f);
    
    return mix(a, b, u);
}

float noise(vec2 seed) {
    vec2 i = floor(seed);
    vec2 f = fract(seed);
    
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));
    
    vec2 u = smoothstep(0, 1, f);
    
    return mix(a, b, u.x) +
    (c - a) * u.y * (1.0 - u.x) +
    (d - b) * u.x * u.y;
}