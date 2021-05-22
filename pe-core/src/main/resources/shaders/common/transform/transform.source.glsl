/**
*
* Transform source file
*
**/

vec3 ndcToClipSpace(const vec2 ndc, const float depth) {
    return vec3(ndc.x, ndc.y, depth) * 2.0 - 1.0;
}

vec3 clipSpaceToViewSpace(const vec3 clipSpace, const mat4 projectionInv) {
    vec4 viewSpace = projectionInv * vec4(clipSpace, 1.0);
    return viewSpace.xyz / viewSpace.w;
}

vec3 viewSpaceToWorldSpace(const vec3 viewSpace, const mat4 viewInv) {
    vec4 worldSpace = viewInv * vec4(viewSpace, 1.0);
    return worldSpace.xyz / worldSpace.w;
}

vec3 calcViewDirection(const vec3 cameraWorldSpace, const vec3 fragWorldSpace) {
    return normalize(cameraWorldSpace - fragWorldSpace);
}

vec3 calculateNormal(const vec2 normal) {
    float x2 = pow(normal.x, 2);
    float y2 = pow(normal.y, 2);
    float z = sqrt(1 - x2 - y2);
    return vec3(normal, z);
}