#include "/shaders/common/fog/fog"
#include "/shaders/common/transform/transform"

#ifndef FD_Y
#define FD_Y 0
#endif

#ifndef FD_XZ
#define FD_XZ 1
#endif

#ifndef FD_XYZ
#define FD_XYZ 2
#endif

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform sampler2D u_depth;
uniform Fog       u_fog;

uniform mat4      u_projectionMatrixInv;
uniform mat4      u_viewMatrixInv;
uniform vec3      u_cameraPosition;
uniform int       u_fogDistance;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

// Methods declaration
float calcDistance(float depth, int fogDistance);

float calcDistanceY(vec3 viewPosition);
float calcDistanceXZ(vec3 viewPosition);
float calcDistanceXYZ(vec3 viewPosition);

// Main
void main(void) {
    float depth = texture(u_depth, v_position).r;

    vec4 colour = texture(u_texture, v_position);

    float distance = calcDistance(depth, u_fogDistance);
    vec3 rgbColour = applyFogColour(distance, u_fog, colour.rgb);

    f_colour = vec4(rgbColour, colour.a);
}

float calcDistance(float depth, int fogDistance) {
    vec3 clipSpace = ndcToClipSpace(v_position, depth);
    vec3 viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);

    switch (fogDistance) {
        case FD_Y: return calcDistanceY(viewPosition);
        case FD_XZ: return calcDistanceXZ(viewPosition);
        case FD_XYZ: return calcDistanceXYZ(viewPosition);
    }
    return -1;
}

float calcDistanceY(vec3 viewPosition) {
    vec3 worldSpace = viewSpaceToWorldSpace(viewPosition, u_viewMatrixInv);
    return abs(worldSpace.y);
}
float calcDistanceXZ(vec3 viewPosition) {
    vec3 worldSpace = viewSpaceToWorldSpace(viewPosition, u_viewMatrixInv);

    return length(worldSpace.xz - u_cameraPosition.xz);
}
float calcDistanceXYZ(vec3 viewPosition) {
    return length(viewPosition.xyz);
}