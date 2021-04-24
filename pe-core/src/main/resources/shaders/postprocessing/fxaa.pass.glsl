/*
*
* Fxaa post processing pass
* implementation by: https://github.com/mattdesl/glsl-fxaa
*
*/

// Definitions
#ifndef FXAA_REDUCE_MIN
    #define FXAA_REDUCE_MIN (1.0/ 128.0)
#endif
#ifndef FXAA_REDUCE_MUL
    #define FXAA_REDUCE_MUL (1.0 / 8.0)
#endif
#ifndef FXAA_SPAN_MAX
    #define FXAA_SPAN_MAX 8.0
#endif

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform ivec2     u_resolution;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

// Methods decleration
void texcoords(vec2 screen_space, vec2 resolution,
               out vec2 uv_NW, out vec2 uv_NE,
               out vec2 uv_SW, out vec2 uv_SE);

vec4 fxaa(sampler2D tex, vec2 screen_space, vec2 resolution,
          vec2 uv_NW, vec2 uv_NE, vec2 uv_SW, vec2 uv_SE);

// Main
void main(void) {
    mediump vec2 uv_NW, uv_NE, uv_SW, uv_SE;

    texcoords(v_position, u_resolution, uv_NW, uv_NE, uv_SW, uv_SE);

    f_colour = fxaa(u_texture, v_position, u_resolution, uv_NW, uv_NE, uv_SW, uv_SE);
}

// Methods
void texcoords(vec2 screen_space, vec2 resolution,
               out vec2 uv_NW, out vec2 uv_NE,
               out vec2 uv_SW, out vec2 uv_SE) {
    vec2 inverseVP = 1.0 / resolution.xy;
    uv_NW = screen_space + vec2(-1.0, -1.0) * inverseVP;
    uv_NE = screen_space + vec2(+1.0, -1.0) * inverseVP;
    uv_SW = screen_space + vec2(-1.0, +1.0) * inverseVP;
    uv_SE = screen_space + vec2(+1.0, +1.0) * inverseVP;
}

vec4 fxaa(sampler2D tex, vec2 screen_space, vec2 resolution,
          vec2 uv_NW, vec2 uv_NE, vec2 uv_SW, vec2 uv_SE) {
    vec3 rgbNW = texture(tex, uv_NW).rgb;
    vec3 rgbNE = texture(tex, uv_NE).rgb;
    vec3 rgbSW = texture(tex, uv_SW).rgb;
    vec3 rgbSE = texture(tex, uv_SE).rgb;
    vec4 texColor = texture(tex, screen_space);
    vec3 rgbM  = texColor.rgb;

    vec3 luma = vec3(0.299, 0.587, 0.114);
    float lumaNW = dot(rgbNW, luma);
    float lumaNE = dot(rgbNE, luma);
    float lumaSW = dot(rgbSW, luma);
    float lumaSE = dot(rgbSE, luma);
    float lumaM  = dot(rgbM,  luma);

    float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
    float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));

    mediump vec2 dir;
    dir.x = -((lumaNW + lumaNE) - (lumaSW + lumaSE));
    dir.y = +((lumaNW + lumaSW) - (lumaNE + lumaSE));

    float dirReduce = max((lumaNW + lumaNE + lumaSW + lumaSE) * (0.25 * FXAA_REDUCE_MUL), FXAA_REDUCE_MIN);

    float rcpDirMin = 1.0 / (min(abs(dir.x), abs(dir.y)) + dirReduce);

    mediump vec2 inverseVP = 1.0 / resolution;
    dir = clamp(dir * rcpDirMin, -FXAA_SPAN_MAX, FXAA_SPAN_MAX) * inverseVP;

    vec3 rgbA = 0.5 * (
        texture(tex, screen_space + dir * (1.0 / 3.0 - 0.5)).rgb +
        texture(tex, screen_space + dir * (2.0 / 3.0 - 0.5)).rgb
    );
    vec3 rgbB = rgbA * 0.5 + 0.25 * (
        texture(tex, screen_space + dir * -0.5).rgb +
        texture(tex, screen_space + dir * +0.5).rgb
    );

    float lumaB = dot(rgbB, luma);
    if ((lumaB < lumaMin) || (lumaB > lumaMax)) {
        return vec4(rgbA, texColor.a);
    } else {
        return vec4(rgbB, texColor.a);
    }
}