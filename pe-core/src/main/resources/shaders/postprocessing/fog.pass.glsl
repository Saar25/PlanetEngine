#include "/shaders/common/fog/fog"
#include "/shaders/common/transform/transform"

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform sampler2D u_depth;
uniform Fog       u_fog;

uniform mat4      u_projectionMatrixInv;
uniform bool      u_applyBackground;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

// Methods declaration
float calcDistance(float depth);

// Main
void main(void) {
    float depth = texture(u_depth, v_position).r;

    if (!u_applyBackground && depth == 1) {
        discard;
    }

    vec4 colour = texture(u_texture, v_position);

    float distance = calcDistance(depth);
    vec3 rgbColour = applyFogColour(distance, u_fog, colour.rgb);

    f_colour = vec4(rgbColour, colour.a);
}

float calcDistance(float depth) {
    vec3 clipSpace = ndcToClipSpace(v_position, depth);
    vec3 viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);

    return length(viewPosition);
}