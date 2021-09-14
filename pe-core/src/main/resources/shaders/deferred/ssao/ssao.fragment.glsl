/**
*
* Ssao fragment shader
*
**/

#include "/shaders/common/transform/transform"

#ifndef KERNEL_SAMPLES
#define KERNEL_SAMPLES 64
#endif

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_colourTexture;
uniform sampler2D u_normalTexture;
uniform sampler2D u_depthTexture;
uniform sampler2D u_noiseTexture;

uniform vec3 u_cameraWorldPosition;
uniform mat4 u_projectionMatrixInv;
uniform mat4 u_viewMatrixInv;

uniform mat4 u_projectionMatrix;
uniform mat4 u_viewMatrix;

uniform vec3[KERNEL_SAMPLES] u_kernel;
uniform vec2 u_noiseScale;
uniform float u_radius;

// Fragment outputs
out vec4 f_colour;

// Global variables
vec3 g_colour;
vec3 g_normal;
float g_depth;
vec3 g_viewPosition;

// Methods declaration

void initBufferValues(void);
void initGlobals(void);

vec3 finalAmbientColour(void);
vec3 finalDiffuseColour(void);
vec3 finalSpecularColour(void);

// Main
void main(void) {
    initBufferValues();
    initGlobals();
    
    vec3 randomVec = texture(u_noiseTexture, v_position * u_noiseScale).rgb * 2 - 1;
    
    vec3 tangent   = normalize(randomVec - g_normal * dot(randomVec, g_normal));
    vec3 bitangent = cross(g_normal, tangent);
    mat3 TBN       = mat3(tangent, bitangent, g_normal);
    
    float bias = .0025;
    
    float occlusion = 0.0;
    for (int i = 0; i < KERNEL_SAMPLES; i++) {
        vec3 point_vs = g_viewPosition + (TBN * u_kernel[i]) * u_radius;
        
        vec4 offset    = u_projectionMatrix * vec4(point_vs, 1.0);
        vec2 sample_uv = (offset.xy / offset.w) * 0.5 + 0.5;
        
        if (sample_uv.x > 0 && sample_uv.x < 1 && sample_uv.y > 0 && sample_uv.y < 1) {
            float sample_d  = texture(u_depthTexture, sample_uv).r;
            vec3  sample_cs = ndcToClipSpace(sample_uv, sample_d);
            vec3  sample_vs = clipSpaceToViewSpace(sample_cs, u_projectionMatrixInv);
            
            float distance   = abs(g_viewPosition.z - sample_vs.z);
            float rangeCheck = smoothstep(0.0, 1.0, u_radius / distance);
            
            occlusion += clamp(sample_vs.z - point_vs.z - bias, 0, 1) * rangeCheck;
        }
    }
    
    occlusion = 1 - (occlusion / KERNEL_SAMPLES);
    f_colour = vec4(g_colour * occlusion, 1);
}

void initBufferValues(void) {
    g_colour = texture(u_colourTexture, v_position).rgb;
    g_normal = texture(u_normalTexture, v_position).rgb;
    g_depth = texture(u_depthTexture, v_position).r;
}

void initGlobals(void) {
    vec3 clipSpace = ndcToClipSpace(v_position, g_depth);
    g_viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);
}