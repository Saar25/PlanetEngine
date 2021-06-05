// Consts
const vec2[] positions = vec2[](
vec2(0, 1), vec2(0, 0),
vec2(1, 1), vec2(1, 0)
);

// Uniforms
uniform vec4  u_bounds;
uniform ivec2 u_bitmapDimensions;
uniform ivec4 u_bitmapBounds;
uniform ivec2 u_resolution;

// Vertex outputs
out vec2 v_uvCoords;

// Methods
vec2 toNdc(vec2 v) {
    return v / u_resolution;
}

vec4 toNdc(vec4 v) {
    return v / u_resolution.xyxy;
}

vec2 currentPosition() {
    return positions[gl_VertexID];
}

vec2 positionByBounds(vec2 position, vec2 dimensions) {
    return position + dimensions * currentPosition();
}

vec2 calculatePosition(vec2 p, vec2 s) {
    vec2 pos = positionByBounds(p, s) * 2 - 1;
    return pos * vec2(1, -1);
}

// Main
void main(void) {
    vec2 p = toNdc(u_bounds.xy);
    vec2 s = toNdc(u_bounds.zw);
    vec2 pos = calculatePosition(p, s);

    gl_Position = vec4(pos, 0, 1);
    gl_ClipDistance[0] = 0;

    vec2 p1 = vec2(u_bitmapBounds.xy) / u_bitmapDimensions.xy;
    vec2 s1 = vec2(u_bitmapBounds.zw) / u_bitmapDimensions.xy;
    v_uvCoords = positionByBounds(p1, s1);
}
