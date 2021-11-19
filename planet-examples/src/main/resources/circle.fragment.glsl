// Consts
const float PI = 3.1415;

// Vertex outputs
in vec2 v_position;

// Uniforms
uniform sampler2D u_texture;
uniform int       u_time;
uniform int       u_radius;

// Fragment outputs
layout (location = 0) out vec4 f_colour;

float atan2(in float y, in float x) {
    return abs(x) > abs(y) ? PI / 2.0 - atan(x, y) : atan(y, x);
}

float atan2(in vec2 point) {
    return atan2(point.x, point.y);
}

void main(void) {
    vec2 position = 2 * v_position - 1;
    float angle = mod(atan2(position) + PI + u_time / 1000., 2 * PI) - PI;

    vec2 position2 = vec2(cos(angle), sin(angle)) * length(position);
    vec4 colour = texture(u_texture, position2);

    vec2 dimensions = gl_FragCoord.xy / v_position;
    vec2 c_position = dimensions / 2 - gl_FragCoord.xy;

    if (dot(c_position, c_position) <= u_radius * u_radius) {
        f_colour.r = angle / PI / 2 + .5;
    } else {
        discard;
    }
}
