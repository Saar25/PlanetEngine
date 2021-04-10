#ifndef LEVELS
#define LEVELS 1
#endif

// Consts
const int hlevels = LEVELS / 2;

const vec2[] blurXy = { vec2(1, 0), vec2(0, 1) };

// Uniforms
uniform sampler2D     u_texture;
uniform ivec2         u_resolution;
uniform float[LEVELS] u_blurLevels;
uniform int           u_verticalBlur;

// Vertex outputs
in vec2 v_position;

// Fragment outputs
out vec4 f_colour;

// Methods declaration
vec4 doBlur(vec2 xy);

// Main
void main(void) {
    vec2 xy = blurXy[u_verticalBlur];
    f_colour = doBlur(xy);
}

// Methods
vec4 doBlur(vec2 xy) {
    vec4 colour = vec4(0);
    vec2 size = u_resolution * xy;
    float step = 1.0 / (size.x + size.y);
    for (int i = -hlevels; i <= hlevels; i++) {
        vec2 coord = v_position + vec2(i, i) * step * xy;
        coord.x = clamp(coord.x, .001, .999);
        coord.y = clamp(coord.y, .001, .999);

        vec4 pixel = texture(u_texture, coord);
        float blur = u_blurLevels[i + hlevels];
        colour += pixel * blur;
    }
    return colour;
}