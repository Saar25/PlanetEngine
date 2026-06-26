const vec2[] positions = vec2[](
vec2(0, 1), vec2(0, 0),
vec2(1, 1), vec2(1, 0)
);

// Uniforms
uniform ivec4 u_bounds;
uniform vec4 u_borders;
uniform ivec2 u_resolution;
uniform ivec4 u_cornersColors;

// Vertex outputs
out vec2 v_position;
out vec4 v_bounds;
out vec4 v_borders;
out vec4 v_backgroundColor;

// Methods
vec2 toNdc(vec2 v) {
    return v / u_resolution;
}

vec4 toNdc(vec4 v) {
    return v / vec4(u_resolution, u_resolution);
}

vec2 currentPosition() {
    return positions[gl_VertexID];
}

vec2 calculatePosition(vec2 p, vec2 s) {
    vec2 current = positions[gl_VertexID];
    vec2 pos = (p + s * current) * 2 - 1;
    return pos * vec2(1, -1);
}

vec2 mapInRectangle(vec4 rectangle, vec2 position) {
    return rectangle.xy + position * rectangle.zw;
}

vec4 getBackgroundColor(int id) {
    uint color = u_cornersColors[id];
    float r = ((color << 0) >> 24);
    float g = ((color << 8) >> 24);
    float b = ((color << 16) >> 24);
    float a = ((color << 24) >> 24);
    return vec4(r, g, b, a) / 255;
}

void main(void) {
    vec4 bordersRect = u_bounds + vec4(-u_borders.xy, u_borders.xy + u_borders.zw);

    vec2 p = toNdc(bordersRect.xy);
    vec2 s = toNdc(bordersRect.zw);
    vec2 pos = calculatePosition(p, s);

    gl_Position = vec4(pos, 0, 1);
    gl_ClipDistance[0] = 0;

    v_position = currentPosition();
    v_position *= bordersRect.zw;
    v_position += bordersRect.xy;
    v_position -= u_bounds.xy;
    v_position /= u_bounds.zw;

    v_bounds = toNdc(u_bounds);
    v_borders = toNdc(bordersRect);

    v_backgroundColor = getBackgroundColor(gl_VertexID);
}
