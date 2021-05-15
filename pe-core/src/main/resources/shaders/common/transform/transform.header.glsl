/**
*
* Transform Header file
*
**/

// Convert ndc [0, 1] to clip space [-1, 1] using depth
vec3 ndcToClipSpace(const vec2 ndc, const float depth);

// Convert clip space [-1, 1] to view space using inverted project matrix
vec3 clipSpaceToViewSpace(const vec3 clipSpace, const mat4 projectionInv);

// Convert view space to world space using inverted view matrix
vec3 viewSpaceToWorldSpace(const vec3 viewSpace, const mat4 viewInv);

// Calculate view direction using camera world space and fragment world space
vec3 calcViewDirection(const vec3 cameraWorldSpace, const vec3 fragWorldSpace);

// Reconstruct the normal value from xy values, works correctly in view space
vec3 calculateNormal(const vec2 normal);