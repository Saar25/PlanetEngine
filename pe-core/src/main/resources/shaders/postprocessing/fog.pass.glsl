#include "/shaders/common/fog/fog"
#include "/shaders/common/transform/transform"

#ifndef FD_DEPTH
#define FD_DEPTH 0
#endif

#ifndef FD_Y
#define FD_Y 1
#endif

#ifndef FD_XZ
#define FD_XZ 2
#endif

#ifndef FD_XYZ
#define FD_XYZ 3
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

float calcAlpha(float fogAmount, int fogDistance);

float calcDistanceDepth(vec3 viewPosition);
float calcDistanceY(vec3 viewPosition);
float calcDistanceXZ(vec3 viewPosition);
float calcDistanceXYZ(vec3 viewPosition);

// Main
void main(void) {
    float depth = texture(u_depth, v_position).r;

    vec4 colour = texture(u_texture, v_position);

    float distance = calcDistance(depth, u_fogDistance);

    float amount = calcFogAmount(distance, u_fog);
    vec3 rgbColour = mix(u_fog.colour, colour.rgb, amount);

    float alpha = calcAlpha(amount, u_fogDistance);
    f_colour = vec4(rgbColour, alpha * colour.a);
}

float calcDistance(float depth, int fogDistance) {
    vec3 clipSpace = ndcToClipSpace(v_position, depth);
    vec3 viewPosition = clipSpaceToViewSpace(clipSpace, u_projectionMatrixInv);

    switch (fogDistance) {
        case FD_DEPTH: return calcDistanceDepth(viewPosition);
        case FD_Y: return calcDistanceY(viewPosition);
        case FD_XZ: return calcDistanceXZ(viewPosition);
        case FD_XYZ: return calcDistanceXYZ(viewPosition);
    }
    return -1.0;
}

float calcAlpha(float fogAmount, int fogDistance) {
    switch (fogDistance) {
        case FD_DEPTH:
        case FD_XZ:
        case FD_XYZ: return fogAmount;
    }
    return 1.0;
}

float calcDistanceDepth(vec3 viewPosition) {
    return abs(viewPosition.z);
}

float calcDistanceY(vec3 viewPosition) {
    vec3 worldSpace = viewSpaceToWorldSpace(viewPosition, u_viewMatrixInv);

    return worldSpace.y;
}

float calcDistanceXZ(vec3 viewPosition) {
    vec3 worldSpace = viewSpaceToWorldSpace(viewPosition, u_viewMatrixInv);

    return length(worldSpace.xz - u_cameraPosition.xz);
}

float calcDistanceXYZ(vec3 viewPosition) {
    return length(viewPosition.xyz);
}