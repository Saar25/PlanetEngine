#include "/shaders/common/noise/noise"

// Vertex outputs
in vec2 v_position;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

#define NUM_OCTAVES 6

float fbm(in vec2 st) {
    float hSin = sin(.5);
    float hCos = cos(.5);
    mat2 rot = mat2(
        +hCos, +hSin,
        -hSin, +hCos
    );
    
    float result = 0.0;
    float attenuation = 0.5;
    for (int i = 0; i < NUM_OCTAVES; ++i) {
        result += attenuation * noise(st);
        st = rot * st * 2 + 100;
        attenuation *= 0.5;
    }
    return result;
}

uniform float u_time;

void main(void) {
    vec2 st = v_position * 3.0;
    
    vec2 q = vec2(0);
    q.x = fbm(st + 0.11 * u_time);
    q.y = fbm(st + vec2(1.0));
    
    vec2 r = vec2(0);
    r.x = fbm(st + 1 * q + vec2(1.7, 9.2) + 0.150 * u_time);
    r.y = fbm(st + 1 * q + vec2(8.3, 2.8) + 0.126 * u_time);
    
    float f = fbm(st + r);
    
    vec3 color = vec3(0.101961, 0.619608, 0.666667);
    
    color = mix(color, vec3(0.666667, 0.666667, 0.498039), clamp((f * f) * 4.0, 0.0, 1.0));
    
    color = mix(color, vec3(0.000000, 0.000000, 0.164706), clamp(length(q), 0.0, 1.0));
    
    color = mix(color, vec3(0.666667, 1.000000, 1.000000), clamp(length(r.x), 0.0, 1.0));
    
    color *= f * f * f + .6 * f * f + .5 * f;
    
    f_colour = vec4(color, 1);
}
